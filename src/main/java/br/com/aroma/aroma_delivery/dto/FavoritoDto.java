package br.com.aroma.aroma_delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoritoDto {

  private Long id;
  private String nome;
  private String descricao;
}
