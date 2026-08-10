package com.meatshopmanager.controller;

import com.meatshopmanager.dto.ExpenseDTO;
import com.meatshopmanager.mapper.ExpenseMapper;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService service;
    private final ExpenseMapper mapper;

    public ExpenseController(ExpenseService service, ExpenseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> create(@RequestBody ExpenseDTO dto) {
        Expense saved = service.save(mapper.toEntity(dto));
        return ResponseEntity.status(201).body(mapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseDTO>> findAll(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                service.findComFiltros(categoriaId, dataInicio, dataFim, pageable)
                        .map(mapper::toDTO)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {
        Expense expense = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(expense));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> update(
            @PathVariable Long id,
            @RequestBody ExpenseDTO dto
    ) {
        Expense updated = service.update(id, mapper.toEntity(dto));
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
