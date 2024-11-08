package br.com.aroma.aroma_delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepDto {

  private String cep;
  private String estado;
  private String logradouro;
  private String localidade;
  private String bairro;
  private String uf;
  private Boolean error;
}
