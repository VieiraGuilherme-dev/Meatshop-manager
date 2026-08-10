package com.meatshopmanager.repository;

import com.meatshopmanager.dto.ExpenseByCategoryDTO;
import com.meatshopmanager.dto.ExpenseByMonthDTO;
import com.meatshopmanager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
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
            e.categoria.nome,
            SUM(e.amount)
        )
        FROM Expense e
        GROUP BY e.categoria.nome
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

    @Query("""
    SELECT e FROM Expense e
    WHERE (:categoriaId IS NULL OR e.categoria.id = :categoriaId)
    AND (:dataInicio IS NULL OR e.expenseDate >= :dataInicio)
    AND (:dataFim IS NULL OR e.expenseDate <= :dataFim)
""")
    Page<Expense> findComFiltros(
            @Param("categoriaId") Long categoriaId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable
    );

    boolean existsByCategoria_Id(Long categoriaId);
}