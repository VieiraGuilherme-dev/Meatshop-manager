package com.meatshopmanager.service;

import com.meatshopmanager.model.*;
import com.meatshopmanager.repository.CategoriaRepository;
import com.meatshopmanager.repository.ExpenseRepository;
import com.meatshopmanager.repository.FuncionarioRepository;
import com.meatshopmanager.strategy.CalculoPagamentoStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolhaPagamentoServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CalculoPagamentoStrategy calculoPagamentoStrategy;

    @InjectMocks
    private FolhaPagamentoService folhaPagamentoService;

    @Test
    void deveGerarDespesaParaCadaFuncionarioAtivo() {

        Funcionario funcionario = new Funcionario("Ana Cristina", "Caixa", new BigDecimal("2000.00"), LocalDate.of(2026, 1, 1));
        funcionario.setId(3L);

        Categoria categoriaSalarios = new Categoria("Salários", TipoCategoria.DESPESA, null);
        categoriaSalarios.setId(9L);

        when(funcionarioRepository.findByAtivo(true)).thenReturn(List.of(funcionario));
        when(categoriaRepository.findByNome("Salários")).thenReturn(Optional.of(categoriaSalarios));
        when(calculoPagamentoStrategy.calcular(funcionario)).thenReturn(new BigDecimal("2000.00"));


        folhaPagamentoService.processarFolhaDoPagamento();

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }
}