package com.outing.expense.service;

import com.outing.commons.api.response.OutingResponse;
import com.outing.expense.api.dto.ExpenseDto;

import java.util.List;

public interface ExpenseCheckService {

     List<ExpenseDto> getAllExpensesById(String expenseId);

      ExpenseDto addExpense(ExpenseDto expenseDto, String expenseIdd, String loggedInUserIdd);
}
