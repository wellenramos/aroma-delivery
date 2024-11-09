package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.PedidoDto;
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

  public List<PedidoDto> listar() {
    Usuario usuario = obterUsuarioAutenticado();
    List<Pedido> pedidos = repository.findAllByUsuario(usuario);
    return mapper.toDtoList(pedidos);
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
        .status(StatusPedidoEnum.PENDENTE)
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
        .status(StatusPagamentoEnum.PENDENTE)
        .dataPagamento(LocalDate.now())
        .metodoPagamento("CARTAO")
        .build();
  }


  private BigDecimal calcularValorTotal(List<ItemCarrinho> itens) {
    return itens.stream()
        .map(item -> item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
