package com.meatshopmanager.repository;

import com.meatshopmanager.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByAtivo(boolean ativo);

    @Query("""
        SELECT f FROM Funcionario f
        WHERE (:ativo IS NULL OR f.ativo = :ativo)
    """)
    Page<Funcionario> findComFiltros(@Param("ativo") Boolean ativo, Pageable pageable);
}
