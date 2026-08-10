package com.meatshopmanager.repository;

import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.TipoCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByTipo(TipoCategoria tipo);
    boolean existsByNome(String nome);
    Optional<Categoria> findByNome(String nome);

    @Query("""
        SELECT c FROM Categoria c
        WHERE (:tipo IS NULL OR c.tipo = :tipo)
    """)
    Page<Categoria> findComFiltros(@Param("tipo") TipoCategoria tipo, Pageable pageable);
}
