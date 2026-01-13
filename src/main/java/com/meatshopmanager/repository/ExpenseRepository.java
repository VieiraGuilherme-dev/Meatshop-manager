package com.meatshopmanager.repository;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
        SELECT SUM(e.amount)
        FROM Expense e
    """)
    BigDecimal getTotalExpenses();

    @Query("""
        SELECT new com.meatshopmanager.dto.ExpenseByCategoryDTO(
            e.category,
            SUM(e.amount)
        )
        FROM Expense e
        GROUP BY e.category
    """)
    List<ExpenseByCategoryDTO> getTotalByCategory();

    @Query("""
        SELECT new com.meatshopmanager.dto.ExpenseByMonthDTO(
            YEAR(e.expenseDate),
            MONTH(e.expenseDate),
            SUM(e.amount)
        )
        FROM Expense e
        GROUP BY YEAR(e.expenseDate), MONTH(e.expenseDate)
        ORDER BY YEAR(e.expenseDate), MONTH(e.expenseDate)
    """)
    List<ExpenseByMonthDTO> getTotalByMonth();
}
