package com.meatshopmanager.service;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.LucroRealDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {

    TotalExpenseDTO getTotalExpenses();
    List<ExpenseByCategoryDTO> getTotalByCategory();
    List<ExpenseByMonthDTO> getTotalByMonth();
    LucroRealDTO getLucroReal();
    byte[] gerarRelatorioExcel(LocalDate dataInicio, LocalDate dataFim);
    byte[] gerarRelatorioPdf(LocalDate dataInicio, LocalDate dataFim);
}
