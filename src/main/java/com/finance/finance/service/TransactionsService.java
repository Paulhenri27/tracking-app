package com.finance.finance.service;

import com.finance.finance.dto.CategoryDTO;
import com.finance.finance.dto.TransactionsDTO;
import com.finance.finance.mapper.Mapper;
import com.finance.finance.model.Category;
import com.finance.finance.model.Transactions;
import com.finance.finance.model.User;
import com.finance.finance.repository.CategoryRepository;
import com.finance.finance.repository.TransactionsRepository;
import com.finance.finance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsService
{

    private static final Logger logger = LoggerFactory.getLogger(TransactionsService.class);
    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public String addTransaction(TransactionsDTO transactionsDTO ,  String userName)
    {

        Transactions transaction = Mapper.mapDTOToTransaction(transactionsDTO);

        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);

        Category category = categoryRepository.findByName(transaction.getCategory().getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        transaction.setCategory(category);

        Transactions savedTransaction = transactionsRepository.save(transaction);

        if (savedTransaction != null)
        {
            logger.info("Transaction added successfully: {}", savedTransaction);
            return "Transaction added successfully";
        }
        else
        {
            logger.error("Transaction could not be added");
            return "Transaction could not be added";
        }
    }

    public List<TransactionsDTO> getUserTransactions(String username)
    {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null)
        {
            return List.of();
        }
        return user.getTransactions().stream()
                .map(Mapper::mapTransactionToDTO)
                .collect(Collectors.toList());
    }



    public List<TransactionsDTO> getUserTransactionsWithFilters(String username, String categoryName, LocalDateTime startDate, LocalDateTime endDate)
    {

        if ((startDate != null && endDate == null) || (startDate == null && endDate != null))
        {
            logger.error("Both startDate and endDate must be provided if one is specified.");
            throw new IllegalArgumentException("Both startDate and endDate must be provided if one is specified.");
        }

        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null)
        {
            logger.error("User not found for email: {}", username);
            return new ArrayList<>();
        }

        Category category = null;
        if (categoryName != null)
        {
            category = categoryRepository.findByName(categoryName)
                    .orElse(null);
            if (category == null)
            {
                logger.error("Category not found for name: {}", categoryName);
                return new ArrayList<>();
            }
        }

        List<Transactions> transactions = transactionsRepository.findByUserAndOptionalFilters(user, category, startDate, endDate);
        if (transactions.isEmpty())
        {
            logger.info("No transactions found for user: {}, category: {}, startDate: {}, endDate: {}",
                    username, categoryName, startDate, endDate);
        }
        else
        {
            logger.info("Transactions found for user: {}, category: {}, startDate: {}, endDate: {}",
                    username, categoryName, startDate, endDate);
        }
        return transactions.stream().map(Mapper::mapTransactionToDTO).collect(Collectors.toList());
    }

    public String deleteTransaction(int transactionId)
    {
        int deleted =  transactionsRepository.deleteTransactionsById(transactionId);

        if(deleted == 0)
        {
            return "Transaction could not be deleted";
        }
        return "Transaction deleted";
    }
}
