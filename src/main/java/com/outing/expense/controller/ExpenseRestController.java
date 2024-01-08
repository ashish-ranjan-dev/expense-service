package com.outing.expense.controller;

//import com.outing.expense.api.Response;
import com.outing.commons.api.response.OutingResponse;
//import com.outing.commons.api.response.Response;
import com.outing.expense.api.dto.ExpenseDto;
import com.outing.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/expense")
@CrossOrigin("http://localhost:4200")
public class ExpenseRestController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping(value = "/add")
    public OutingResponse<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto newExpenseDto = expenseService.addExpense(expenseDto);
        List<String> messages = new ArrayList<>();
        messages.add("Expense Added Successfully");
//        Response<ExpenseDto> response = new Response<>(newExpenseDto,messages);
//        return new ResponseEntity<>(response,HttpStatus.OK);
        return new OutingResponse<>(newExpenseDto,HttpStatus.OK,messages);
    }

    @PutMapping(value = "/{expenseId}/update")
    public OutingResponse<ExpenseDto> updateExpense(@PathVariable(value = "expenseId") String expenseId,@RequestBody ExpenseDto ExpenseDto)  {
        ExpenseDto newExpenseDto = expenseService.updateExpense(expenseId, ExpenseDto);
        List<String> messages = new ArrayList<>();
        messages.add("Expense Updated Successfully");
//        Response<ExpenseDto> response = new Response<>(newExpenseDto,messages);
//        return new ResponseEntity<>(response,HttpStatus.OK);
        return new OutingResponse<>(newExpenseDto,HttpStatus.OK,messages);
    }

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public OutingResponse<List<ExpenseDto>> getAllExpensesByUserId(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = "1000000",required = false)Integer pageSize
    ){
        List<ExpenseDto> expenseDtos = expenseService.getAllExpensesByUserId(pageNumber,pageSize);
        List<String> messages = new ArrayList<>();
//        messages.add("Expense fetched successfully for current user");
//        Response<List<ExpenseDto>> response = new Response<>(expenseDtos,new ArrayList<>());
//        return new ResponseEntity<>(response,HttpStatus.OK);
        return new OutingResponse<>(expenseDtos,HttpStatus.OK);
    }


    @GetMapping(value = "/outing/{outingId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public OutingResponse<List<ExpenseDto>> getAllOutingExpensesByUserId(
            @PathVariable(value = "outingId") String outingId,@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = "1000000",required = false)Integer pageSize
    ){
        List<ExpenseDto> expenseDtos = expenseService.getAllOutingExpensesByUserId(outingId,pageNumber,pageSize);
        return new OutingResponse<>(expenseDtos,HttpStatus.OK);
    }


    @DeleteMapping(value = "/{expenseId}/delete")
    public OutingResponse<Void> deleteExpenseById(@PathVariable String expenseId){
        expenseService.deleteExpenseById(expenseId);
        List<String> messages = new ArrayList<>();
        messages.add("Expense Deleted Successfully");
//        Response<Void> response = new Response<>(null,messages);
//        return new ResponseEntity<>(response,HttpStatus.OK);
        return new OutingResponse<>(null,HttpStatus.OK,messages);
    }

    @PostMapping(value = "/approve")
    public OutingResponse<ExpenseDto> approveExpense(@RequestBody Map<String,String> approvalRequest){
        ExpenseDto newExpenseDto = expenseService.approveExpense(approvalRequest);
        List<String> messages = new ArrayList<>();
        messages.add("Expense Approval Successfully updated");
//        Response<ExpenseDto> response = new Response<>(newExpenseDto,messages);
//        return new ResponseEntity<>(response,HttpStatus.OK);
        return new OutingResponse<>(newExpenseDto,HttpStatus.OK,messages);
    }

}
