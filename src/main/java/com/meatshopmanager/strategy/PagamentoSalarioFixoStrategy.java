package com.meatshopmanager.strategy;


import com.meatshopmanager.model.Funcionario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PagamentoSalarioFixoStrategy implements CalculoPagamentoStrategy {

    @Override
    public BigDecimal calcular(Funcionario funcionario){
        return funcionario.getSalario();
    }

}
