package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
