package br.com.aroma.aroma_delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemAdicionalDto {

    private Long id;
    private ProdutoResumidoDto adicional;
}
