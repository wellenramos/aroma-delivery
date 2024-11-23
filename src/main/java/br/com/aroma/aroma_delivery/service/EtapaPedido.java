package br.com.aroma.aroma_delivery.service;

import br.com.aroma.aroma_delivery.dto.AcompanharDto.StatusEtapaDto;
import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class EtapaPedido {

  private StatusPedidoEnum status;

  public List<StatusEtapaDto> processar() {
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

}
