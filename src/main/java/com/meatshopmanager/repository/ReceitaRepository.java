package com.meatshopmanager.repository;

import com.meatshopmanager.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    boolean existsByCategoria_Id(Long categoriaId);

    @Query("""
        SELECT SUM(r.valor)
        FROM Receita r
    """)
    BigDecimal getTotalReceitas();

    @Query("""
        SELECT r FROM Receita r
        WHERE (:categoriaId IS NULL OR r.categoria.id = :categoriaId)
        AND (:dataInicio IS NULL OR r.data >= :dataInicio)
        AND (:dataFim IS NULL OR r.data <= :dataFim)
    """)
    Page<Receita> findComFiltros(
            @Param("categoriaId") Long categoriaId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable
    );
}
