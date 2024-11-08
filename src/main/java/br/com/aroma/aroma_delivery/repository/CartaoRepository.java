package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}
