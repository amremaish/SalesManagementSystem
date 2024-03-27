package com.sales.service.impl;

import java.util.Optional;

import com.sales.dao.entites.User;
import com.sales.dao.repo.UserRepo;
import com.sales.error.CustomException;
import com.sales.security.MyUserDetails;
import com.sales.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements UserDetailsService, AuthService {

    @Autowired
    private UserRepo userRepo;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public MyUserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByPhoneNumber(phoneNumber);
        user.orElseThrow(() -> new CustomException("The request is rejected because the credentials are invalid"));
        return user.map(MyUserDetails::new).get();
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    @Override
    public User getFindUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new CustomException("User is not found"));
    }

    public Object register(User user) {
        user.setPassword(getEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return user;

    }
}
