package com.meatshopmanager.meatshop_manager.service;

import com.meatshopmanager.meatshop_manager.model.Expense;
import com.meatshopmanager.meatshop_manager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }
}
