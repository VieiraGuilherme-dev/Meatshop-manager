package com.meatshopmanager.dto;

import java.math.BigDecimal;

public record LucroRealDTO(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal lucro) {
}
