package br.com.aroma.aroma_delivery.service.integracao;

import br.com.aroma.aroma_delivery.dto.ViaCepDto;
import br.com.aroma.aroma_delivery.exception.ViaCepIntegrationException;
import br.com.aroma.aroma_delivery.feign.ViaCepClient;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepIntegrationService {

  private final ViaCepClient client;

  public Optional<ViaCepDto> consultarPorCep(String zipCode) {
    try {
      Optional<ViaCepDto> address = client.findAddressByZipCode(zipCode);
      if (address.isPresent() && Objects.nonNull(address.get().getError())
          && address.get().getError()) {
        return Optional.empty();
      }
      return address;
    } catch (FeignException.NotFound e) {
      return Optional.empty();
    } catch (FeignClientException e) {
      throw new ViaCepIntegrationException(
          "Não foi possível consultar o endereço pelo cep " + zipCode, e);
    }
  }
}