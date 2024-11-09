package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Pedido;
import br.com.aroma.aroma_delivery.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

  List<Pedido> findAllByUsuario(Usuario usuario);
}
