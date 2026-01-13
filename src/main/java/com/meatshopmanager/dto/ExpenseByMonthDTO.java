package com.meatshopmanager.dto;

import java.math.BigDecimal;

public class ExpenseByMonthDTO {

    private Integer year;
    private Integer month;
    private BigDecimal total;

    public ExpenseByMonthDTO(Integer year, Integer month, BigDecimal total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
