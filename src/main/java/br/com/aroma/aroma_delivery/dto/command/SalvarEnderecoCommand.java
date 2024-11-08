package br.com.aroma.aroma_delivery.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalvarEnderecoCommand {

    private Long id;

    @NotBlank(message = "Cep é obrigatório")
    @Size(message = "Limite do campo cep excedido", max = 20)
    private String cep;

    @Size(message = "Limite do campo numero excedido", max = 50)
    private String numero;

    @Size(message = "Limite do campo complemento excedido", max = 255)
    private String complemento;

    @Size(message = "Limite do campo bairro excedido", max = 100)
    private String bairro;

    @Size(message = "Limite do campo cidade excedido", max = 100)
    private String cidade;

    @Size(message = "Limite do campo estado excedido", max = 50)
    private String estado;

    private Boolean principal;
}
