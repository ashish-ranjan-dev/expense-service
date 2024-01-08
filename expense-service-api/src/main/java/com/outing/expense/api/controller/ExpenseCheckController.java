package com.outing.expense.api.controller;

import com.outing.commons.api.response.OutingResponse;
import com.outing.expense.api.dto.ExpenseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:8085",value = "expense-service")
public interface ExpenseCheckController {

//    @GetMapping(value = "/expense-service")
//    ResponseEntity<List<ExpenseDto>> getAllExpensesById(@RequestBody String expenseId);

    @GetMapping(value = "/expense-service/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ExpenseDto>> getAllExpensesById(@PathVariable("expenseId") String expenseId);

    @PostMapping(value = "/expense-service/{expenseId}/user/{userId}/add-expense", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto, @PathVariable("expenseId") String expenseId,@PathVariable("userId") String userId);
}
