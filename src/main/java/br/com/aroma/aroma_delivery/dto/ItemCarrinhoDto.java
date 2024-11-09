package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.dto.enums.TamanhoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoDto {

    private Long id;
    private Long produtoId;
    private Long carrinhoId;
    private Integer quantidade;
    private String observacao;
    private TamanhoEnum tamanhoCopo;
}
