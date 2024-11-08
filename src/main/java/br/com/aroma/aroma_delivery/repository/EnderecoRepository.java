package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Endereco;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

  Optional<Endereco> findByCep(String cep);
}
