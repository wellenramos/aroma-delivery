package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.CartaoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarCartaoCommand;
import br.com.aroma.aroma_delivery.model.Cartao;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    CartaoDto toDto(Cartao cartao);
    Cartao toEntity(SalvarCartaoCommand command);
    List<CartaoDto> toCartaoDtoList(List<Cartao> cartoes);
}
