package com.finance.finance.service;

import com.finance.finance.dto.UserDTO;
import com.finance.finance.dto.UserLoginDTO;
import com.finance.finance.dto.UserRegistrationDTO;
import com.finance.finance.mapper.Mapper;
import com.finance.finance.model.User;
import com.finance.finance.repository.UserRepository;
import com.finance.finance.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authentication;

    @Autowired
    private JwtUtil jwtUtil;


    public UserDTO registerUser(UserRegistrationDTO userDto)
    {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser =  userRepository.save(user);

        return Mapper.mapUserToDTO(savedUser);
    }


    public String verify(UserLoginDTO loginDto)
    {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());

        Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if(auth.isAuthenticated())
        {
            User newUser = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            return jwtUtil.generateToken(newUser.getEmail() , newUser.getRole());
        }

        return "fail";

    }

    public UserDTO getUserNameByEmail(String email)
    {
        return userRepository.findUserNameByEmail(email);
    }

}
