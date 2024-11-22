package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.AcompanharDto;
import br.com.aroma.aroma_delivery.dto.AcompanharDto.ItemDto;
import br.com.aroma.aroma_delivery.dto.AcompanharDto.StatusEtapaDto;
import br.com.aroma.aroma_delivery.dto.AvaliacaoDto;
import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.command.AvaliacaoPedidoCommand;
import br.com.aroma.aroma_delivery.dto.command.SalvarPedidoCommand;
import br.com.aroma.aroma_delivery.dto.enums.StatusPagamentoEnum;
import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.PedidoMapper;
import br.com.aroma.aroma_delivery.model.Cartao;
import br.com.aroma.aroma_delivery.model.Endereco;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import br.com.aroma.aroma_delivery.model.Pagamento;
import br.com.aroma.aroma_delivery.model.Pedido;
import br.com.aroma.aroma_delivery.model.Usuario;
import br.com.aroma.aroma_delivery.repository.CartaoRepository;
import br.com.aroma.aroma_delivery.repository.EnderecoRepository;
import br.com.aroma.aroma_delivery.repository.ItemCarrinhoRepository;
import br.com.aroma.aroma_delivery.repository.PedidoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

  private final PedidoRepository repository;
  private final SecurityService securityService;
  private final UsuarioRepository usuarioRepository;
  private final EnderecoRepository enderecoRepository;
  private final ItemCarrinhoRepository itemCarrinhoRepository;
  private final CartaoRepository cartaoRepository;
  private final AvaliacaoService avaliacaoService;
  private final PedidoMapper mapper;


  @Transactional
  public PedidoDto salvar(SalvarPedidoCommand command) {
    Usuario usuario = obterUsuarioAutenticado();
    Endereco endereco = obterEndereco(command.getEnderecoId());
    Cartao cartao = obterCartao(command.getCartaoId());
    List<ItemCarrinho> itens = itemCarrinhoRepository.findAllById(command.getItens());

    Pedido pedido = criarPedido(usuario, endereco, itens);
    associarItensAoPedido(itens, pedido);

    Pagamento pagamento = criarPagamento(pedido, cartao);
    pedido.setPagamento(pagamento);

    return mapper.toDto(repository.save(pedido));
  }

  public List<AcompanharDto> acompanhar() {
    Usuario usuario = obterUsuarioAutenticado();
    List<Pedido> pedidos = repository.findAllByUsuarioAndStatusNotIn(usuario, List.of(StatusPedidoEnum.CONCLUIDO));

    return pedidos.stream().map(pedido -> {

      List<ItemDto> itens = pedido.getItens().stream().map(item ->
          ItemDto.builder()
          .id(item.getId())
          .nome(item.getProduto().getNome())
          .descricao(item.getProduto().getDescricao())
          .preco(item.calcularValorTotalItem())
          .build()).toList();

      List<StatusEtapaDto> etapas = mapearEtapas(pedido.getStatus());

      return AcompanharDto.builder()
          .id(pedido.getId())
          .itens(itens)
          .valorTotal(pedido.getValorTotal())
          .etapas(etapas)
          .build();
    }).toList();
  }

  private List<StatusEtapaDto> mapearEtapas(StatusPedidoEnum status) {
    List<StatusEtapaDto> etapas = new ArrayList<>();

    etapas.add(StatusEtapaDto.builder()
        .etapa("Pedido foi criado")
        .completo(status.ordinal() >= StatusPedidoEnum.PENDENTE.ordinal())
        .build());

    etapas.add(StatusEtapaDto.builder()
        .etapa("Pagamento concluído")
        .completo(status.ordinal() >= StatusPedidoEnum.PAGO.ordinal())
        .build());

    etapas.add(StatusEtapaDto.builder()
        .etapa("Pedido está sendo preparado")
        .completo(status.ordinal() >= StatusPedidoEnum.PROCESSANDO.ordinal())
        .build());

    etapas.add(StatusEtapaDto.builder()
        .etapa("Pedido foi enviado")
        .completo(status.ordinal() >= StatusPedidoEnum.ENVIADO.ordinal())
        .build());

    etapas.add(StatusEtapaDto.builder()
        .etapa("Pedido foi entregue")
        .completo(status == StatusPedidoEnum.ENTREGUE)
        .build());

    return etapas;
  }

  public AvaliacaoDto avaliar(Long pedidoId, AvaliacaoPedidoCommand command) {
    return avaliacaoService.avaliar(pedidoId, command);
  }

  private Usuario obterUsuarioAutenticado() {
    String email = securityService.getAuthenticatedUser().getUsername();
    return usuarioRepository.findByLogin(email)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
  }

  private Endereco obterEndereco(Long enderecoId) {
    return enderecoRepository.findById(enderecoId)
        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
  }

  private Cartao obterCartao(Long cartaoId) {
    return cartaoRepository.findById(cartaoId)
        .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
  }

  private Pedido criarPedido(Usuario usuario, Endereco endereco, List<ItemCarrinho> itens) {
    return Pedido.builder()
        .usuario(usuario)
        .endereco(endereco)
        .dataSolicitacao(LocalDate.now())
        .status(StatusPedidoEnum.PAGO)
        .valorTotal(calcularValorTotal(itens))
        .favorito(false)
        .itens(itens)
        .build();
  }

  private void associarItensAoPedido(List<ItemCarrinho> itens, Pedido pedido) {
    itens.forEach(it -> it.setPedido(pedido));
    itemCarrinhoRepository.saveAll(itens);
  }

  private Pagamento criarPagamento(Pedido pedido, Cartao cartao) {
    return Pagamento.builder()
        .pedido(pedido)
        .cartao(cartao)
        .valor(pedido.getValorTotal())
        .status(StatusPagamentoEnum.APROVADO)
        .dataPagamento(LocalDate.now())
        .metodoPagamento("CARTAO")
        .build();
  }


  private BigDecimal calcularValorTotal(List<ItemCarrinho> itens) {
    return itens.stream()
        .map(item -> item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public List<PedidoDto> historico() {
    Usuario usuario = obterUsuarioAutenticado();
    List<Pedido> pedidos = repository.findAllByUsuarioAndStatusIn(usuario, List.of(StatusPedidoEnum.CONCLUIDO));
    return mapper.toDtoList(pedidos);
  }
}
