package com.meatshopmanager.repository;

import com.meatshopmanager.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    boolean existsByCategoria_Id(Long categoriaId);
}
