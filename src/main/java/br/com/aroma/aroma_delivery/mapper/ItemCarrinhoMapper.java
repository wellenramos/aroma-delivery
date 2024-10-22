package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.ItemCarrinhoDto;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {

    ItemCarrinho toEntity(ItemCarrinhoDto itemCarrinhoDto);
}