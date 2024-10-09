package br.com.aroma.aroma_delivery.repository;

import br.com.aroma.aroma_delivery.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
