package com.finance.finance.controller;

import com.finance.finance.dto.CategoryDTO;
import com.finance.finance.dto.TransactionsDTO;
import com.finance.finance.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionController
{

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionsService transactionsService;


    @PostMapping("/add-transaction")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionsDTO transactionsDTO, Principal principal)
    {
        String username = principal.getName();

        try
        {
            String result = transactionsService.addTransaction(transactionsDTO, username);
            logger.info("Transaction added successfully for user: {}", username);
            return ResponseEntity.ok(result);
        }
        catch (Exception e)
        {
            logger.error("Failed to add transaction for user {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction could not be added: " + e.getMessage());
        }
    }

    @GetMapping("/get-transactions")
    public ResponseEntity<List<TransactionsDTO>> getTransactions(Principal principal)
    {
        String username = principal.getName();
        try
        {
            List<TransactionsDTO> userTransactions = transactionsService.getUserTransactions(username);
            logger.info("Transactions found for user: {}", username);
            return ResponseEntity.ok(userTransactions);
        }
        catch (Exception e)
        {
            logger.error("Failed to find transactions for user {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }


    @GetMapping("/get-transactions-by-date")
    public ResponseEntity<?> getTransactions
    (
            Principal principal,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    )
    {
        String username = principal.getName();

        logger.info("Received parameters - username: {}, categoryName: {}, startDate: {}, endDate: {}",
                username, categoryName, startDate, endDate);
        try
        {
            List<TransactionsDTO> userTransactions = transactionsService.getUserTransactionsWithFilters(username, categoryName, startDate, endDate);
            logger.info("Transactions found for user: {}, category: {}, startDate: {}, endDate: {}",
                    username, categoryName, startDate, endDate);
            return ResponseEntity.ok(userTransactions);
        }
        catch (Exception e)
        {
            logger.error("Failed to find transactions for user {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/delete-transaction")
    public ResponseEntity<?> deleteTransaction(@RequestParam int transactionId)
    {
        try
        {
            logger.info("Transaction deleted for user: {}", transactionId);
            return ResponseEntity.ok(transactionsService.deleteTransaction(transactionId));
        }
        catch (Exception e)
        {
            logger.error("Failed to delete transaction for user {}: {}", transactionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
}
