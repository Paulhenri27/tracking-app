package com.finance.finance.repository;

import com.finance.finance.dto.UserDTO;
import com.finance.finance.model.Transactions;
import com.finance.finance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    @Query("SELECT t FROM Transactions t JOIN t.user u WHERE u.email = :email")
    List<Transactions> findTransactionsByUserEmail(@Param("email") String email);

    @Query("SELECT new com.finance.finance.dto.UserDTO(u.firstName, u.lastName) FROM User u WHERE u.email = :email")
    UserDTO findUserNameByEmail(@Param("email") String email);


}
