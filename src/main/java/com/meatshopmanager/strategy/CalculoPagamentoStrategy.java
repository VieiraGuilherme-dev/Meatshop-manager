package com.meatshopmanager.strategy;

import com.meatshopmanager.model.Funcionario;

import java.math.BigDecimal;

public interface CalculoPagamentoStrategy {

    BigDecimal calcular(Funcionario funcionario);
}