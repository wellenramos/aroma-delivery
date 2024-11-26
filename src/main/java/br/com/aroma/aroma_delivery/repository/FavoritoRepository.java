package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Favorito;
import br.com.aroma.aroma_delivery.model.Produto;
import br.com.aroma.aroma_delivery.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

  List<Favorito> findByUsuario(Usuario usuario);

  Optional<Favorito> findByUsuarioAndProduto(Usuario usuario, Produto produto);
}
