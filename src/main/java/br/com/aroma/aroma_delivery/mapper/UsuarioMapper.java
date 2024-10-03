package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDTO);
}
