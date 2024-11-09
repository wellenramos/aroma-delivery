package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.AvaliacaoDto;
import br.com.aroma.aroma_delivery.dto.command.AvaliacaoPedidoCommand;
import br.com.aroma.aroma_delivery.model.Avaliacao;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    Avaliacao toAvaliacao(AvaliacaoPedidoCommand command);

    List<AvaliacaoDto> toAvaliacaoList(List<Avaliacao> avaliacoes);

    @Mapping(source = "pedido.id", target = "pedidoId")
    AvaliacaoDto toDto(Avaliacao avaliacao);
}
