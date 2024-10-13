package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(Categoria categoria);
}
