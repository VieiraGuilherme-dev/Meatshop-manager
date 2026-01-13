package com.meatshopmanager.dto;

import com.meatshopmanager.model.ExpenseCategory;

import java.math.BigDecimal;

public class ExpenseByCategoryDTO {

    private ExpenseCategory category;
    private BigDecimal total;

    public ExpenseByCategoryDTO(ExpenseCategory category, BigDecimal total) {
        this.category = category;
        this.total = total;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
