package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.model.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    private String nome;
    private String email;
    private Perfil perfil;
}
