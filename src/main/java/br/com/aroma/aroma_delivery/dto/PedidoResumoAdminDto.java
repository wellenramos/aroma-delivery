package br.com.aroma.aroma_delivery.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PedidoResumoAdminDto {

  private Long id;
  private BigDecimal valorTotal;
  private String usuarioSolicitante;
  private String dataPedido;
}
