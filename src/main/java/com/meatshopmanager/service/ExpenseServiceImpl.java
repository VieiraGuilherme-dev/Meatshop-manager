package com.meatshopmanager.service;

import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Expense save(Expense expense) {
        return repository.save(expense);
    }

    @Override
    public List<Expense> findAll() {
        return repository.findAll();
    }

    @Override
    public Expense findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id)
                );
    }

    @Override
    public Expense update(Long id, Expense expense) {
        Expense existing = findById(id);

        existing.setDescription(expense.getDescription());
        existing.setCategory(expense.getCategory());
        existing.setAmount(expense.getAmount());
        existing.setExpenseDate(expense.getExpenseDate());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Expense existing = findById(id);
        repository.delete(existing);
    }
}
