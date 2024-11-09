package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Avaliacao;
import br.com.aroma.aroma_delivery.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

  List<Avaliacao> findByPedido_Usuario(Usuario usuario);
}
