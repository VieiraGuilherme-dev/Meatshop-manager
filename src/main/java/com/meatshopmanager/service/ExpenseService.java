package com.meatshopmanager.service;

import com.meatshopmanager.model.Expense;

import java.util.List;

public interface ExpenseService {

    Expense save(Expense expense);

    List<Expense> findAll();

    Expense findById(Long id);

    Expense update(Long id, Expense expense);

    void delete(Long id);
}
