package com.finance.finance.repository;

import com.finance.finance.model.Category;
import com.finance.finance.model.Transactions;
import com.finance.finance.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer>
{

    List<Transactions> findByUser_Email(String email);




    @Query("SELECT t FROM Transactions t WHERE t.user = :user " +
            "AND (:category IS NULL OR t.category = :category) " +
            "AND (:startDate IS NULL OR t.date >= :startDate) " +
            "AND (:endDate IS NULL OR t.date <= :endDate)")
    List<Transactions> findByUserAndOptionalFilters(
            @Param("user") User user,
            @Param("category") Category category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Transactions t WHERE t.id = :id")
    int deleteTransactionsById(@Param("id") int id);
}
