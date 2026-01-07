package com.meatshopmanager.meatshop_manager.service;

import com.meatshopmanager.meatshop_manager.exception.ResourceNotFoundException;
import com.meatshopmanager.meatshop_manager.model.Expense;
import com.meatshopmanager.meatshop_manager.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository repository;
    
    public ExpenseServiceImpl(ExpenseRepository repository){
        this.repository = repository;
    }

    @Override
    public Expense save(Expense expense){
        return repository.save(expense);
    }

    @Override
    public List<Expense> findAll(){
        return repository.findAll();
    }

    @Override
    public Expense findById(Long id){
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

    }
    @Override
    public Expense update(Long id, Expense expense){
        Expense existing = findById(id);

        existing.setDescription(expense.getDescription());
        existing.setCategory(expense.getCategory());
        existing.setAmount(expense.getAmount());
        existing.setExpenseDate(expense.getExpenseDate());
        
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
