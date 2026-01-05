package com.meatshopmanager.meatshop_manager.controller;

import com.meatshopmanager.meatshop_manager.model.Expense;
import com.meatshopmanager.meatshop_manager.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody Expense expense){
        return  ResponseEntity.ok(service.save(expense));

    }

    @GetMapping
    public ResponseEntity<List<Expense>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(
            @PathVariable Long id,
            @RequestBody Expense expense
    ){
        return ResponseEntity.ok(service.update(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
