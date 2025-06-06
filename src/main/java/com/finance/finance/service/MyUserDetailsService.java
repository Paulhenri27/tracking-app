package com.finance.finance.service;

import com.finance.finance.model.User;
import com.finance.finance.model.UserPrincipal;
import com.finance.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(user == null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException(email);
        }

        return new UserPrincipal(user);
    }
}
