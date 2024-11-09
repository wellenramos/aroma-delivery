package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.PedidoDto;
import br.com.aroma.aroma_delivery.model.Pedido;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoDto toDto(Pedido pedido);
    List<PedidoDto> toDtoList(List<Pedido> pedidos);
}
