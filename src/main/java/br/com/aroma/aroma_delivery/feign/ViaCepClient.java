package br.com.aroma.aroma_delivery.feign;

import br.com.aroma.aroma_delivery.dto.ViaCepDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCepClient", url = "${viacep.integration.uri}")
public interface ViaCepClient {

  @GetMapping("/{zipCode}/json")
  Optional<ViaCepDto> findAddressByZipCode(@PathVariable("zipCode") String zipCode);
}