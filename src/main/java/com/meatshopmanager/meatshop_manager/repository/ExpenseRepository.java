package com.meatshopmanager.meatshop_manager.repository;

import com.meatshopmanager.meatshop_manager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
