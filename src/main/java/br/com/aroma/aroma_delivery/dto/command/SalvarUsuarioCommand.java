package br.com.aroma.aroma_delivery.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalvarUsuarioCommand {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(message = "Limite do campo nome excedido", max = 200)
    private String nome;

    @NotBlank(message = "Login é obrigatório")
    @Size(message = "Limite do campo login excedido", max = 200)
    private String login;

    @Size(message = "Limite do campo email excedido", max = 100)
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    @Size(message = "Limite do campo senha excedido", max = 50)
    private String senha;
}
