package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.ProdutoDto;
import br.com.aroma.aroma_delivery.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    ProdutoDto toDto(Produto produto);
}
