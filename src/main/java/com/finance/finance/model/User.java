package com.finance.finance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String password;
    private String Role;

    @Column(unique = true , nullable = false)
    private String email;



    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Transactions> transactions = new ArrayList<>();

    public User(String role, String password, String email, String firstName, Integer id, String lastName , List<Transactions> transactions) {
        Role = role;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.transactions = transactions;
    }

    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }



    public String getFirstName() {
        return firstName;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }





}
