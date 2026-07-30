package com.meatshopmanager.service;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.LucroRealDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.repository.ExpenseRepository;
import com.meatshopmanager.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository repository;
    private final ReceitaRepository receitaRepository;

    public DashboardServiceImpl(ExpenseRepository repository, ReceitaRepository receitaRepository) {
        this.repository = repository;
        this.receitaRepository = receitaRepository;
    }

    @Override
    public TotalExpenseDTO getTotalExpenses() {
        BigDecimal total = repository.getTotalExpenses();
        return new TotalExpenseDTO(total == null ? BigDecimal.ZERO : total);
    }

    @Override
    public List<ExpenseByCategoryDTO> getTotalByCategory() {
        return repository.getTotalByCategory();
    }

    @Override
    public List<ExpenseByMonthDTO> getTotalByMonth() {
        return repository.getTotalByMonth();
    }

    @Override
    public LucroRealDTO getLucroReal() {
        BigDecimal totalDespesas = repository.getTotalExpenses();
        totalDespesas = totalDespesas == null ? BigDecimal.ZERO : totalDespesas;

        BigDecimal totalReceitas = receitaRepository.getTotalReceitas();
        totalReceitas = totalReceitas == null ? BigDecimal.ZERO : totalReceitas;

        BigDecimal lucro = totalReceitas.subtract(totalDespesas);

        return new LucroRealDTO(totalReceitas, totalDespesas, lucro);
    }
}