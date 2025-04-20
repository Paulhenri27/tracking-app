package com.finance.finance.dto;

import java.util.List;
public class UserDTO
{
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private List<TransactionsDTO> transactions;

    public UserDTO(String lastName, String firstName, Integer id, String email, String role, List<TransactionsDTO> transactions) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.email = email;
        this.role = role;
        this.transactions = transactions;
    }

    public UserDTO() {}

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<TransactionsDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsDTO> transactions) {
        this.transactions = transactions;
    }
}
