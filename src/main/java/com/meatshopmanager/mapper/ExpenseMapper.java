package com.meatshopmanager.mapper;

import com.meatshopmanager.dto.ExpenseDTO;
import com.meatshopmanager.exception.ResourceNotFoundException;
import com.meatshopmanager.model.Categoria;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.repository.CategoriaRepository;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    private final CategoriaRepository categoriaRepository;

    public ExpenseMapper(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Expense toEntity(ExpenseDTO dto) {
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada com id: " + dto.getCategoriaId()));
        expense.setCategoria(categoria);

        return expense;
    }

    public ExpenseDTO toDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());

        if (expense.getCategoria() != null) {
            dto.setCategoriaId(expense.getCategoria().getId());
            dto.setCategoriaNome(expense.getCategoria().getNome());
        }

        return dto;
    }
}