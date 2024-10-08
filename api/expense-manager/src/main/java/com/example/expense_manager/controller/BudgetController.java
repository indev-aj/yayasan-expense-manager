package com.example.expense_manager.controller;

import com.example.expense_manager.entity.Budget;
import com.example.expense_manager.entity.Category;
import com.example.expense_manager.entity.Expense;
import com.example.expense_manager.entity.User;
import com.example.expense_manager.service.BudgetService;
import com.example.expense_manager.service.CategoryService;
import com.example.expense_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@RestController
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    // Create a new budget
    @PostMapping("/budget")
    public ResponseEntity<String> createBudget(@RequestParam String username,
                                               @RequestParam Long categoryId,
                                               @RequestParam BigDecimal amount,
                                               @RequestParam Date periodStart,
                                               @RequestParam Date periodEnd) {
        User user = userService.findByUsername(username);
        Category category = categoryService.findById(categoryId);
        List<Budget> currentBudgets = budgetService.getBudgetsByUser(user);

        if (currentBudgets != null) {
            for (Budget b : currentBudgets) {
                if (category.getName().equals(b.getCategory().getName())) {
                    BigDecimal currentAmount = b.getAmount();
                    BigDecimal updatedAmount = currentAmount.add(amount);

                    b.setAmount(updatedAmount);
                    budgetService.save(b);

                    return ResponseEntity.ok("Budget saved successfully!");
                }
            }
        }

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(amount);
        budget.setPeriodStart(periodStart);
        budget.setPeriodEnd(periodEnd);

        budgetService.save(budget);

        return ResponseEntity.ok("Budget saved successfully!");
    }

    // Get all budgets for a specific user
    @GetMapping("/budgets")
    public ResponseEntity<List<Budget>> getBudgetsByUser(@RequestParam String username) {
        User user = userService.findByUsername(username);
        List<Budget> budgets = budgetService.getBudgetsByUser(user);
        return ResponseEntity.ok(budgets);
    }

    // Optional: Get all budgets
    @GetMapping("/allBudgets")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(budgets);
    }

    @PutMapping("/updateBudgetsWithExchangeRate")
    public ResponseEntity<String> updateBudgetsWithExchangeRate(@RequestParam String username, @RequestParam BigDecimal exchangeRate) {
        User user = userService.findByUsername(username);

        // Fetch all expenses for the user
        List<Budget> budgets = budgetService.getBudgetsByUser(user);

        for (Budget budget : budgets) {
            BigDecimal originalAmount = budget.getAmount();
            BigDecimal convertedAmount = originalAmount.multiply(exchangeRate);

            budget.setAmount(convertedAmount);
            budgetService.save(budget); // Save the updated expense
        }

        return ResponseEntity.ok("Expenses updated with the exchange rate of " + exchangeRate);
    }
}