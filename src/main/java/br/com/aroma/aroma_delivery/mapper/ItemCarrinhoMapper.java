package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.ItemCarrinhoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarItemCarrinhoCommand;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {

    @Mapping(target = "adicionais", ignore = true)
    ItemCarrinho toEntity(SalvarItemCarrinhoCommand command);
    ItemCarrinhoDto toDto(ItemCarrinho itemCarrinho);
}
