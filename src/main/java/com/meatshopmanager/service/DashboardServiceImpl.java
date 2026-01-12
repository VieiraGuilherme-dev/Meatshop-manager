package com.meatshopmanager.service;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.repository.ExpenseRepository;
import com.meatshopmanager.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository repository;

    public DashboardServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
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
}
