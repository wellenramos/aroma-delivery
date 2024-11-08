package br.com.aroma.aroma_delivery.mapper;

import br.com.aroma.aroma_delivery.dto.EnderecoBaseDto;
import br.com.aroma.aroma_delivery.dto.EnderecoDto;
import br.com.aroma.aroma_delivery.dto.command.SalvarEnderecoCommand;
import br.com.aroma.aroma_delivery.model.Endereco;
import br.com.aroma.aroma_delivery.model.EnderecoBase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDto toDto(Endereco endereco);
    Endereco toEntity(SalvarEnderecoCommand command);

    EnderecoBaseDto toEnderecoBaseDto(EnderecoBase enderecoBase);
    EnderecoBaseDto toEnderecoDto(Endereco endereco);
}
