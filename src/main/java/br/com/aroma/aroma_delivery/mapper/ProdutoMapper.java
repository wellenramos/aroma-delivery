package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.UsuarioDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.dto.command.SalvarUsuarioCommand;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDto toDto(Produto produto);
    Produto toEntity(SalvarProdutoCommand command);
    List<ProdutoDto> toDtoList(List<Produto> produtos);

}

