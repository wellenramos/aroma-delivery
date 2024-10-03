package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
