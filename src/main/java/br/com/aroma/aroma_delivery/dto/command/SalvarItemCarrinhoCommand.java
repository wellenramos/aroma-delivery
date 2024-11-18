package br.com.aroma.aroma_delivery.dto.command;

import br.com.aroma.aroma_delivery.dto.enums.TamanhoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalvarItemCarrinhoCommand {

    private Long id;

    @NotNull(message = "O campo produtoId é obrigatório")
    private Long produtoId;

    private Long carrinhoId;

    @NotNull(message = "O campo quantidade é obrigatório")
    private Integer quantidade;

    @Size(message = "Limite do campo observacao excedido", max = 500)
    private String observacao;

    @NotNull(message = "O campo tamanhoCopo é obrigatório")
    private TamanhoEnum tamanhoCopo;

    private List<Long> adicionais;
}
