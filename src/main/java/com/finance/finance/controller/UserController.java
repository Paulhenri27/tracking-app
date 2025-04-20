package com.finance.finance.controller;

import com.finance.finance.dto.UserDTO;
import com.finance.finance.dto.UserLoginDTO;
import com.finance.finance.dto.UserRegistrationDTO;
import com.finance.finance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody UserRegistrationDTO userDto)
    {
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDTO loginDto)
    {
        return userService.verify(loginDto);
    }

    @PostMapping("/get-user")
    public UserDTO getUser(@RequestBody UserDTO userDTO)
    {
        return userService.getUserNameByEmail(userDTO.getEmail());
    }

}
