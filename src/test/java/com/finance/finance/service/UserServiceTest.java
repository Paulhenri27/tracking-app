package com.finance.finance.service;

import com.finance.finance.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void getUser()
    {
        UserDTO userDto = userService.getUserNameByEmail("petit@gmail.com");

        System.out.println(userDto.toString());
    }

}