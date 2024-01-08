package com.outing.expense.repository;

import com.outing.expense.model.ExpenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel,String> {
    Optional<ExpenseModel> findById(String id);
}
