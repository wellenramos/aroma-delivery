package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.CarrinhoDto;
import br.com.aroma.aroma_delivery.model.Carrinho;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    CarrinhoDto toDto(Carrinho carrinho);
}
