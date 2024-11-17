package br.com.aroma.aroma_delivery.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarrinhoDto {

    private Long id;
    private UsuarioDto usuario;
    private List<ItemCarrinhoDto> itens;
}
