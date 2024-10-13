package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId AND CAST(unaccent(LOWER(p.nome)) AS text) LIKE CONCAT('%', CAST(unaccent(LOWER(:nome)) AS text), '%')")
    List<Produto> buscarPorNome(Long categoriaId, String nome);
}
