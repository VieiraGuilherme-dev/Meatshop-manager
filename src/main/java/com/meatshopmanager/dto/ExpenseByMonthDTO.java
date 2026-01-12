package com.meatshopmanager.dto;

import java.math.BigDecimal;

public record ExpenseByMonthDTO(
        Integer year,
        Integer month,
        BigDecimal total
) {}
