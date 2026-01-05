package com.meatshopmanager.meatshop_manager.controller;

import com.meatshopmanager.meatshop_manager.dto.ExpenseDTO;
import com.meatshopmanager.meatshop_manager.mapper.ExpenseMapper;
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

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> create(@RequestBody ExpenseDTO dto) {
        Expense saved = service.save(ExpenseMapper.toEntity(dto));
        return ResponseEntity.ok(ExpenseMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> findAll() {
        return ResponseEntity.ok(
                service.findAll()
                        .stream()
                        .map(ExpenseMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {
        Expense expense = service.findById(id);
        return ResponseEntity.ok(ExpenseMapper.toDTO(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> update(
            @PathVariable Long id,
            @RequestBody ExpenseDTO dto
    ) {
        Expense updated = service.update(id, ExpenseMapper.toEntity(dto));
        return ResponseEntity.ok(ExpenseMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
