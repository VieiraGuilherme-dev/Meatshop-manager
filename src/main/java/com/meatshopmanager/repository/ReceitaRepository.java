package com.meatshopmanager.repository;

import com.meatshopmanager.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    boolean existsByCategoria_Id(Long categoriaId);

    @Query("""
    SELECT SUM(r.valor)
    FROM Receita r
""")
    BigDecimal getTotalReceitas();
}
