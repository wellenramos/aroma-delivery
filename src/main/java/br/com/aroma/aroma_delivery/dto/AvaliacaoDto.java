package br.com.aroma.aroma_delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvaliacaoDto {

  private Long id;
  private Long pedidoId;
  private Integer nota;
  private String comentario;
}
