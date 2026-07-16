package com.meatshopmanager.repository;

import com.meatshopmanager.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByAtivo(boolean ativo);
}
