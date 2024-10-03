package br.com.aroma.aroma_delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(message = "Limite do campo nome excedido", max = 200)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Size(message = "Limite do campo email excedido", max = 100)
    private String email;

    @NotNull(message = "Perfil é obrigatório")
    private Long perfilId;
}
