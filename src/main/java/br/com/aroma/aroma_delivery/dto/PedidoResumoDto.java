package br.com.aroma.aroma_delivery.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PedidoResumoDto {

  private List<AcompanharDto> pedidosEmAndamento;
  private List<HistoricoAgrupadoDto> historico;
}
