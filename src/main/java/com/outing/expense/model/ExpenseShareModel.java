package com.outing.expense.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expense_share")
public class ExpenseShareModel {
    @Id
    private String id;
    @Column(name = "expense_id")
    private String expenseId;

    @Column(name = "user_id")
   private String userId;


    @Column(name = "name")
    private String name;

    @Column(name = "beared_amount")
   private int bearerAmount;

    @Column(name = "beneficiary_amount")
   private int beneficiaryAmount;

    @Column(name = "status")
    private String status;

    public ExpenseShareModel() {
    }

    public ExpenseShareModel(String id, String expenseId, String userId, int bearerAmount, int beneficiaryAmount, String status, String name) {
        this.id = id;
        this.expenseId = expenseId;
        this.userId = userId;
        this.bearerAmount = bearerAmount;
        this.beneficiaryAmount = beneficiaryAmount;
        this.status = status;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String approvalStatus) {
        this.status = approvalStatus;
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

    public int getBearerAmount() {
        return bearerAmount;
    }

    public void setBearerAmount(int paidAmount) {
        this.bearerAmount = paidAmount;
    }

    public int getBeneficiaryAmount() {
        return beneficiaryAmount;
    }

    public void setBeneficiaryAmount(int shareAmount) {
        this.beneficiaryAmount = shareAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
