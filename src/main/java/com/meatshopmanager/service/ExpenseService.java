package com.meatshopmanager.service;

import com.meatshopmanager.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    Expense save(Expense expense);

    List<Expense> findAll();

    Page<Expense> findComFiltros(Long categoriaId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Expense findById(Long id);

    Expense update(Long id, Expense expense);

    void delete(Long id);
}
