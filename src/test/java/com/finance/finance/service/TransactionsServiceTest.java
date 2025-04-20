package com.finance.finance.service;

import com.finance.finance.dto.TransactionsDTO;
import com.finance.finance.dto.UserDTO;
import com.finance.finance.model.Transactions;
import com.finance.finance.model.User;
import com.finance.finance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionsServiceTest
{
    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;






}