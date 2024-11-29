package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Carrinho;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import br.com.aroma.aroma_delivery.model.Produto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

  Optional<ItemCarrinho> findByCarrinhoAndProduto(Carrinho carrinho, Produto produto);
}
