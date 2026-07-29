package com.meatshopmanager.repository;

import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByTipo(TipoCategoria tipo);
    boolean existsByNome(String nome);
    Optional<Categoria> findByNome(String nome);
}
