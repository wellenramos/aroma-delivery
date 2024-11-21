package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Cartao;
import br.com.aroma.aroma_delivery.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

  Optional<Cartao> findByUsuarioAndPrincipal(Usuario usuario, boolean principal);
  List<Cartao> findByUsuario(Usuario usuario);
}
