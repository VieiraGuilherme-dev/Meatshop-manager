package com.meatshopmanager.meatshop_manager.mapper;

import com.meatshopmanager.meatshop_manager.dto.ExpenseDTO;
import com.meatshopmanager.meatshop_manager.model.Expense;

public class ExpenseMapper {
    public static Expense toEntity(ExpenseDTO dto){
        Expense expense = new Expense();

        expense.setId(dto.getId());
        expense.setDescription(dto.getDescription());
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        return expense;
    }

    public static ExpenseDTO toDTO(Expense expense){
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setCategory(expense.getCategory());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());
        return dto;
    }
}

