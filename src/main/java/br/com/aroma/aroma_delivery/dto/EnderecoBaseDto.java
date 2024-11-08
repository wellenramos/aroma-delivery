package br.com.aroma.aroma_delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoBaseDto {

  private Long id;
  private String cep;
  private String uf;
  private String estado;
  private String cidade;
  private String bairro;
}
