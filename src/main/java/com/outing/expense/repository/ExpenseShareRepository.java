package com.outing.expense.repository;

import com.outing.expense.model.ExpenseShareModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShareModel,String> {

    List<ExpenseShareModel> findByExpenseId(String expenseId);

    @Modifying
    @Transactional
    @Query("UPDATE ExpenseShareModel esm SET esm.beneficiaryAmount = :newShareAmount , esm.bearerAmount = :newPaidAmount  WHERE esm.userId = :userId AND esm.expenseId = :expenseId")
    void updateFriendExpense(@Param("userId") String userId, @Param("expenseId") String expenseId, @Param("newShareAmount") int newShareAmount, @Param("newPaidAmount") int newPaidAmount);

    boolean existsByUserIdAndExpenseId(String userId,String expenseId);

    @Modifying
    @Transactional
    void deleteByUserIdAndExpenseId(String curFriendId,String expenseId);

    Page<ExpenseShareModel> findByUserId(String userId,Pageable pageable);

    ExpenseShareModel findByUserIdAndExpenseId(String userId, String expenseId);

    @Query("SELECT es FROM ExpenseShareModel es " +
            "INNER JOIN ExpenseModel e ON es.expenseId = e.id " +
            "WHERE es.userId = :loggedInUserId AND e.deletedStatus = false " +
            "ORDER BY e.date ASC")
    List<ExpenseShareModel> findByUserIdAndExpenseNotDeleted(String loggedInUserId, Pageable pageable);

    @Query("SELECT es FROM ExpenseShareModel es " +
            "INNER JOIN ExpenseModel e ON es.expenseId = e.id " +
            "WHERE es.userId = :loggedInUserId AND e.deletedStatus = false AND e.outingId = :outingId " +
            "ORDER BY e.date ASC")
    List<ExpenseShareModel> findByUserIdAndExpenseNotDeleted(String loggedInUserId, String outingId);

    @Query("SELECT es FROM ExpenseShareModel es " +
            "INNER JOIN ExpenseModel e ON es.expenseId = e.id " +
            "WHERE es.expenseId = :expenseId AND e.deletedStatus = false " +
            "ORDER BY e.date ASC")
    List<ExpenseShareModel> findByExpenseIdAndExpenseNotDeleted(String expenseId);
}
