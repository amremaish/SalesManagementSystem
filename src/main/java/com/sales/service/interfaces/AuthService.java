package com.sales.service.interfaces;

import com.sales.dao.entites.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AuthService {
    public PasswordEncoder getEncoder();

    public UserDetails loadUserByUsername(String email);

    public User getCurrentUser();
    public User getFindUserById(long id);

    public Object register(User user);
}
