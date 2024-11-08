package br.com.aroma.aroma_delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto {

  private Long id;
  private String cep;
  private String numero;
  private String complemento;
  private String bairro;
  private String cidade;
  private String estado;
  private Boolean principal;

}