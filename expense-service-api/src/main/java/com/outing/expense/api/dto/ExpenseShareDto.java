package com.outing.expense.api.dto;

public class ExpenseShareDto {

    private String id;

    private String expenseId;

    private String userId;

    private String name;

    private int bearerAmount;

    private int beneficiaryAmount;

    private String status;

    public ExpenseShareDto() {
    }

    public ExpenseShareDto(String id, String expenseId, String userId, String name, int bearerAmount, int beneficiaryAmount, String status) {
        this.id = id;
        this.expenseId = expenseId;
        this.userId = userId;
        this.name = name;
        this.bearerAmount = bearerAmount;
        this.beneficiaryAmount = beneficiaryAmount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBearerAmount() {
        return bearerAmount;
    }

    public void setBearerAmount(int bearerAmount) {
        this.bearerAmount = bearerAmount;
    }

    public int getBeneficiaryAmount() {
        return beneficiaryAmount;
    }

    public void setBeneficiaryAmount(int beneficiaryAmount) {
        this.beneficiaryAmount = beneficiaryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
