package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.PedidoAdminDetalheDto;
import br.com.aroma.aroma_delivery.dto.PedidoAdminDetalheDto.ItemPedidoAdminDto;
import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.dto.PedidoResumoAdminDto;
import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import br.com.aroma.aroma_delivery.mapper.PedidoMapper;
import br.com.aroma.aroma_delivery.model.Endereco;
import br.com.aroma.aroma_delivery.model.Pedido;
import br.com.aroma.aroma_delivery.repository.PedidoRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoAdminService {

  private final PedidoRepository repository;
  private final PedidoMapper mapper;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy", new Locale("pt", "BR"));

  public List<PedidoResumoAdminDto> listarPedidosPorSituacao(StatusPedidoEnum status) {
    List<StatusPedidoEnum> statusPedidoEnums = new java.util.ArrayList<>(List.of(status));

    if (StatusPedidoEnum.ENTREGUE.equals(status)) {
      statusPedidoEnums.add(StatusPedidoEnum.CONCLUIDO);
    }

    List<Pedido> pedidos = repository.findByStatusIn(statusPedidoEnums);
    return pedidos.stream().map(it -> PedidoResumoAdminDto.builder()
        .id(it.getId())
        .dataPedido(formatter.format(it.getDataSolicitacao()))
        .usuarioSolicitante(it.getUsuario().getNome())
        .valorTotal(it.getValorTotal())
        .build())
        .toList();
  }

  public PedidoDto atualizarStatus(Long id, StatusPedidoEnum status) {
    Pedido pedido = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

    pedido.setStatus(status);
    repository.save(pedido);
    return mapper.toDto(pedido);
  }

  public PedidoAdminDetalheDto obterPedidoResumo(Long id) {
    Pedido pedido = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

    return PedidoAdminDetalheDto.builder()
        .id(pedido.getId())
        .cliente(pedido.getUsuario().getNome())
        .endereco(montarEndereco(pedido.getEndereco()))
        .total(pedido.getValorTotal())
        .status(pedido.getStatus())
        .itens(pedido.getItens().stream().map(it ->
                ItemPedidoAdminDto.builder()
                    .produto(it.getProduto().getNome())
                    .quantidade(it.getQuantidade())
                    .preco(it.calcularValorTotalItem())
                    .build()
            ).toList())
        .build();
  }

  private String montarEndereco(Endereco endereco) {
    StringBuilder sb = new StringBuilder("Quadra ");
    sb.append(endereco.getNumero()).append(" ");
    sb.append(endereco.getComplemento()).append(" ");
    sb.append(endereco.getBairro()).append(" ");
    sb.append(endereco.getCidade()).append("/");
    sb.append(endereco.getEstado()).append(" - ");
    sb.append(endereco.getCep());
    return sb.toString();
  }
}
