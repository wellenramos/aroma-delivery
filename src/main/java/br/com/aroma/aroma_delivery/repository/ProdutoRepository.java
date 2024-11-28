package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Categoria;
import br.com.aroma.aroma_delivery.model.Produto;
import feign.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(Categoria categoria);

    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId AND CAST(unaccent(LOWER(p.nome)) AS text) LIKE CONCAT('%', CAST(unaccent(LOWER(:nome)) AS text), '%')")
    List<Produto> buscarPorNomeECategoria(Long categoriaId, String nome);

    @Query("SELECT p FROM Produto p WHERE CAST(unaccent(LOWER(p.nome)) AS text) LIKE CONCAT('%', CAST(unaccent(LOWER(:nome)) AS text), '%')")
    List<Produto> buscarPorNome(String nome);

    @Modifying
    @Query("UPDATE Produto p SET p.mediaAvaliacao = (" +
        "    SELECT AVG(pe.notaAvaliacao) " +
        "    FROM Pedido pe " +
        "    JOIN ItemCarrinho ic ON ic.pedido.id = pe.id " +
        "    WHERE ic.produto.id = p.id " +
        "    AND pe.notaAvaliacao IS NOT NULL" +
        ") WHERE p.id IN :productIds")
    void calcularMedidoProdutos(@Param("productIds") List<Long> productIds);

    @Modifying
    @Query(value = "DELETE FROM produto_adicional WHERE produto_id = :produtoId", nativeQuery = true)
    void deleteAdicionaisByProdutoId(@Param("produtoId") Long produtoId);

    @Query(value = "SELECT COUNT(*) > 0 FROM produto_adicional WHERE produto_id = :produtoId", nativeQuery = true)
    boolean verificarAdicionaisByProdutoId(@Param("produtoId") Long produtoId);
}
