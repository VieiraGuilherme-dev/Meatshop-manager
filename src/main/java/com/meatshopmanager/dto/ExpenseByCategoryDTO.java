package com.meatshopmanager.dto;

import java.math.BigDecimal;

public class ExpenseByCategoryDTO {

    private String categoriaNome;
    private BigDecimal total;

    public ExpenseByCategoryDTO(String categoriaNome, BigDecimal total) {
        this.categoriaNome = categoriaNome;
        this.total = total;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
