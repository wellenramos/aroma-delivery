package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.model.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoResumidoDto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Categoria categoria;
    private SituacaoProdutoEnum situacao;
}
