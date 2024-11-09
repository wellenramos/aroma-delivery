package br.com.aroma.aroma_delivery.dto.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalvarPedidoCommand {

    @NotNull(message = "O campo enderecoId é obrigatório")
    private Long enderecoId;

    @NotNull(message = "O campo cartaoId é obrigatório")
    private Long cartaoId;

    @NotEmpty(message = "O campo itens é obrigatório")
    private List<Long> itens;
}
