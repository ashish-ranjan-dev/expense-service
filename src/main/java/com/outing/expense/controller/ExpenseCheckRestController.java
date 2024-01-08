package com.outing.expense.controller;

import com.netflix.discovery.converters.Auto;
import com.outing.commons.api.response.OutingResponse;
import com.outing.expense.api.controller.ExpenseCheckController;
import com.outing.expense.api.dto.ExpenseDto;
import com.outing.expense.service.ExpenseCheckService;
import com.outing.expense.service.ExpenseService;
import com.outing.expense.service.impl.ExpenseCheckImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExpenseCheckRestController implements ExpenseCheckController {

    @Autowired
    ExpenseCheckService expenseCheckService;

    @Override
    public ResponseEntity<List<ExpenseDto>> getAllExpensesById(String expenseId){
        List<ExpenseDto> expenseDtos = expenseCheckService.getAllExpensesById(expenseId);
        return new ResponseEntity<>(expenseDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExpenseDto> addExpense(ExpenseDto expenseDto, String expenseIdd, String loggedInUserIdd){
        ExpenseDto newExpenseDto = expenseCheckService.addExpense(expenseDto, expenseIdd,loggedInUserIdd);
        return new ResponseEntity<>(newExpenseDto,HttpStatus.OK);
    }
}


