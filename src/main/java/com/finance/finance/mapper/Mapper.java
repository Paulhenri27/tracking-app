package com.finance.finance.mapper;

import com.finance.finance.dto.CategoryDTO;
import com.finance.finance.dto.TransactionsDTO;
import com.finance.finance.dto.UserDTO;
import com.finance.finance.model.Category;
import com.finance.finance.model.Transactions;
import com.finance.finance.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper
{

    public static UserDTO mapUserToDTO(User user)
    {
        UserDTO dto = new UserDTO();
        //dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        if(user.getTransactions() != null)
        {
            List<TransactionsDTO> transactions = user.getTransactions().stream()
                    .map(Mapper::mapTransactionToDTO)
                    .collect(Collectors.toList());

            dto.setTransactions(transactions);
        }
        return dto;
    }

    public static TransactionsDTO mapTransactionToDTO(Transactions transaction)
    {
        TransactionsDTO dto = new TransactionsDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());

        if(transaction.getCategory() != null)
        {
            dto.setCategory(mapCategoryToDTO(transaction.getCategory()));
        }

        return dto;
    }

    public static CategoryDTO mapCategoryToDTO(Category category)
    {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }



    public static User mapDTOToUser(UserDTO dto)
    {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        if (dto.getTransactions() != null)
        {
            List<Transactions> transactions = dto.getTransactions().stream()
                    .map(Mapper::mapDTOToTransaction)
                    .collect(Collectors.toList());

            user.setTransactions(transactions);
            transactions.forEach(t -> t.setUser(user));
        }

        return user;
    }

    public static Transactions mapDTOToTransaction(TransactionsDTO dto)
    {
        Transactions transaction = new Transactions();
        transaction.setId(dto.getId());
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());

        if (dto.getCategory() != null)
        {
            transaction.setCategory(mapDTOToCategory(dto.getCategory()));
        }

        return transaction;
    }

    public static Category mapDTOToCategory(CategoryDTO dto)
    {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
}
