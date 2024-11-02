package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarProdutoCommand;
import br.com.aroma.aroma_delivery.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDto toDto(Produto produto);

    @Mapping(target = "adicionais", ignore = true)
    Produto toEntity(SalvarProdutoCommand command);

    List<ProdutoDto> toDtoList(List<Produto> produtos);

}

