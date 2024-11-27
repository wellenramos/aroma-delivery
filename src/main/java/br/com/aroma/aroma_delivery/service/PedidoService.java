package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.AcompanharDto;
import br.com.aroma.aroma_delivery.dto.AcompanharDto.ItemDto;
import br.com.aroma.aroma_delivery.dto.AcompanharDto.StatusEtapaDto;
import br.com.aroma.aroma_delivery.dto.HistoricoAgrupadoDto;
import br.com.aroma.aroma_delivery.dto.HistoricoDto;
import br.com.aroma.aroma_delivery.dto.HistoricoDto.ItemHistoricoDto;
import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.PedidoResumoDto;
import br.com.aroma.aroma_delivery.dto.PedidoResumoDto.PedidoResumoDtoBuilder;
import br.com.aroma.aroma_delivery.dto.command.SalvarPedidoCommand;
import br.com.aroma.aroma_delivery.dto.enums.StatusPagamentoEnum;
import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.PedidoMapper;
import br.com.aroma.aroma_delivery.mapper.PedidoMapperImpl;
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
import br.com.aroma.aroma_delivery.repository.ProdutoRepository;
import br.com.aroma.aroma_delivery.repository.UsuarioRepository;
import ch.qos.logback.core.util.StringUtil;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
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
  private final PedidoMapper mapper;
  private final ProdutoRepository produtoRepository;
  private final PedidoMapperImpl pedidoMapperImpl;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy", new Locale("pt", "BR"));

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

  public PedidoResumoDto acompanharMeusPedidos() {
    Usuario usuario = obterUsuarioAutenticado();
    List<Pedido> pedidos = repository.findAllByUsuarioAndStatusNotIn(usuario, List.of(StatusPedidoEnum.CONCLUIDO));
    PedidoResumoDtoBuilder builder = PedidoResumoDto.builder();

    if (!pedidos.isEmpty())
      return builder.pedidosEmAndamento(montarPedidosEmAndamento(pedidos)).historico(List.of()).build();
    return builder.historico(montarHistoricoAgrupado(usuario)).pedidosEmAndamento(List.of()).build();
  }

  public List<HistoricoAgrupadoDto> montarHistoricoAgrupado(Usuario usuario) {
    List<Pedido> pedidos = repository.findAllByUsuarioAndStatusIn(usuario, List.of(StatusPedidoEnum.CONCLUIDO));

    List<HistoricoDto> historicos = pedidos.stream()
        .map(pedido -> HistoricoDto.builder()
            .id(pedido.getId())
            .status(pedido.getStatus().name())
            .notaAvaliacao(pedido.getNotaAvaliacao())
            .itens(pedido.getItens().stream()
                .map(it -> ItemHistoricoDto.builder()
                    .id(it.getId())
                    .nome(it.getProduto().getNome())
                    .quantidade(it.getQuantidade())
                    .build())
                .toList())
            .build())
        .toList();

    return historicos.stream()
        .collect(Collectors.groupingBy(historicoDto ->
            pedidos.stream()
                .filter(p -> p.getId().equals(historicoDto.getId()))
                .findFirst()
                .map(Pedido::getDataSolicitacao)
                .orElseThrow(() -> new IllegalStateException("Pedido não encontrado"))
                .format(formatter)
        ))
        .entrySet().stream()
        .map(entry -> HistoricoAgrupadoDto.builder()
            .dataSolicitacao(StringUtil.capitalizeFirstLetter(entry.getKey()))
            .itens(entry.getValue())
            .build())
        .toList();
  }

  private List<AcompanharDto> montarPedidosEmAndamento(List<Pedido> pedidos) {
    return pedidos.stream().map(pedido -> {

      List<ItemDto> itens = pedido.getItens().stream().map(item ->
          ItemDto.builder()
              .id(item.getId())
              .nome(item.getProduto().getNome())
              .descricao(item.getProduto().getDescricao())
              .preco(item.calcularValorTotalItem())
              .build()).toList();

      List<StatusEtapaDto> etapas = new EtapaPedido(pedido.getStatus()).processar();

      return AcompanharDto.builder()
          .id(pedido.getId())
          .itens(itens)
          .valorTotal(pedido.getValorTotal())
          .etapas(etapas)
          .build();
    }).toList();
  }

  @Transactional
  public void avaliar(Long pedidoId, Integer nota) {
    Pedido pedido = repository.findById(pedidoId)
        .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    pedido.setNotaAvaliacao(nota);

    List<Long> produtoIds = pedido.getItens().stream()
        .map(it -> it.getProduto().getId()).toList();

    produtoRepository.calcularMedidoProdutos(produtoIds);
    repository.save(pedido);
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
        .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));
  }

  private Pedido criarPedido(Usuario usuario, Endereco endereco, List<ItemCarrinho> itens) {
    return Pedido.builder()
        .usuario(usuario)
        .endereco(endereco)
        .dataSolicitacao(LocalDate.now())
        .status(StatusPedidoEnum.PENDENTE)
        .valorTotal(calcularValorTotal(itens))
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

  public PedidoDto confimarRecebimento(Long id) {
    Pedido pedido = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

    pedido.setStatus(StatusPedidoEnum.CONCLUIDO);
    repository.save(pedido);
    return mapper.toDto(pedido);
  }
}
