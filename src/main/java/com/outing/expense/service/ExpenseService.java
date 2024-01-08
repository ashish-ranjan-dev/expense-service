package com.outing.expense.service;

import com.outing.expense.api.dto.ExpenseDto;

import java.util.List;
import java.util.Map;

public interface ExpenseService {
    public ExpenseDto addExpense(ExpenseDto expenseDto);

    public ExpenseDto updateExpense(String expenseId, ExpenseDto ExpenseDto);

    public void deleteExpenseById(String expenseId);

    public List<ExpenseDto> getAllExpensesByUserId(Integer pageNumber,Integer pageSize);

    public List<ExpenseDto> getAllOutingExpensesByUserId(String outingId, Integer pageNumber,Integer pageSize);

    public ExpenseDto approveExpense(Map<String, String> approvalRequest);
}
