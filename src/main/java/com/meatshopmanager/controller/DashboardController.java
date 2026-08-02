package com.meatshopmanager.controller;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.dto.TotalExpenseDTO;
import com.meatshopmanager.model.Expense;
import com.meatshopmanager.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meatshopmanager.dto.LucroRealDTO;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/total")
    public TotalExpenseDTO totalExpenses(){
        return service.getTotalExpenses();
    }

    @GetMapping("/by-category")
    public List<ExpenseByCategoryDTO> byCategory(){
        return service.getTotalByCategory();
    }

    @GetMapping("/by-month")
    public List<ExpenseByMonthDTO> byMonth(){
        return service.getTotalByMonth();
    }

    @GetMapping("/lucro")
    public LucroRealDTO lucroReal(){
        return service.getLucroReal();
    }
}
