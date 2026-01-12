package com.meatshopmanager.service;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.model.Expense;

import java.util.List;

public interface DashboardService {

    TotalExpenseDTO getTotalExpenses();
    List<ExpenseByCategoryDTO> getTotalByCategory();
    List<ExpenseByMonthDTO> getTotalByMonth();
}
