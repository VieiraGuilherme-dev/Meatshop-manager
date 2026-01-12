package com.meatshopmanager.dto;

import java.math.BigDecimal;

public record ExpenseByCategoryDTO(
        String category,
        BigDecimal total
) {}
