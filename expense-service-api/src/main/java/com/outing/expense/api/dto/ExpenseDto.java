package com.outing.expense.api.dto;

public class ExpenseDto {

    private String id;
    private String description;

    private int totalAmount;

    private String date;

    private String creatorId;
    private ExpenseShareDto[] expenseDetails;

    private String outingId;

    public ExpenseDto() {
    }

    public ExpenseDto(String id, String description, int totalAmount, String date, String creatorId, ExpenseShareDto[] expenseDetails) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.date = date;
        this.creatorId = creatorId;
        this.expenseDetails = expenseDetails;
    }

    public ExpenseDto(String id, String description, int totalAmount, String date, String creatorId, ExpenseShareDto[] expenseDetails, String outingId) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.date = date;
        this.creatorId = creatorId;
        this.expenseDetails = expenseDetails;
        this.outingId = outingId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ExpenseShareDto[] getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(ExpenseShareDto[] expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getOutingId() {
        return outingId;
    }

    public void setOutingId(String outingId) {
        this.outingId = outingId;
    }
}
