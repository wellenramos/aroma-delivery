package br.com.aroma.aroma_delivery.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HistoricoAgrupadoDto {

  private String dataSolicitacao;
  private List<HistoricoDto> itens;
}