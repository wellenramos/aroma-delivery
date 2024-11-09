package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.dto.enums.SituacaoProdutoEnum;
import br.com.aroma.aroma_delivery.model.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProdutoDto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Categoria categoria;
    private SituacaoProdutoEnum situacao;
    private List<ItemAdicionalDto> adicionais;
}
