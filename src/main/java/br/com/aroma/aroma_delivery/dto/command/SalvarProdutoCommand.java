package br.com.aroma.aroma_delivery.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SalvarProdutoCommand {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(message = "Limite do campo nome excedido", max = 100)
    private String nome;

    @Size(message = "Limite do campo descrição excedido", max = 500)
    private String descricao;

    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "O campo categoria é obrigatório")
    private Long categoriaId;

}
