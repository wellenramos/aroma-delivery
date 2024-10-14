package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Usuario;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String username);
}
