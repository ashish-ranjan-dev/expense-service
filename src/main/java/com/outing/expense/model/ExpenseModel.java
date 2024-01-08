package com.outing.expense.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expense")
public class ExpenseModel {
    @Id
    private String id;

    @Column(name = "date")
    private String date;

    @Column(name = "amount")
    private int totalAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted_status")
    private boolean deletedStatus;

    @Column(name = "creator")
    private String creatorId;

    @Column(name= "outing_id")
    private String outingId;

    public ExpenseModel() {
    }

    public ExpenseModel(String id, String date, int totalAmount, String description, boolean deletedStatus, String creatorId) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.description = description;
        this.deletedStatus = deletedStatus;
        this.creatorId = creatorId;
    }

    public ExpenseModel(String id, String date, int totalAmount, String description, boolean deletedStatus, String creatorId, String outingId) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.description = description;
        this.deletedStatus = deletedStatus;
        this.creatorId = creatorId;
        this.outingId = outingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int amount) {
        this.totalAmount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String note) {
        this.description = note;
    }

    public boolean getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }


    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creator_id) {
        this.creatorId = creator_id;
    }


    public void setOutingId(String outingId) {
        this.outingId = outingId;
    }

    public String getOutingId() {
        return outingId;
    }
}
