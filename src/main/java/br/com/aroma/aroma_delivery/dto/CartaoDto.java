package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoDto {

  private Long id;
  private String numero;
  private String titular;
  private String tipo;
  private Short validadeMes;
  private Short validadeAno;
  private String cvv;
  private String bandeira;
  private Boolean principal;
  private Usuario usuario;
}
