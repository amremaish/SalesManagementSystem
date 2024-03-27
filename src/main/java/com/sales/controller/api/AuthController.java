package com.sales.controller.api;

import com.sales.dao.entites.User;
import com.sales.dao.dto.LoginRequest;
import com.sales.error.CustomException;
import com.sales.error.CustomResponse;
import com.sales.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.sales.security.JWTResponse;
import com.sales.security.TokenUtils;
import com.sales.service.impl.AuthServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")

public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public Object login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));
        } catch (DisabledException dis) {
            throw new CustomException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new CustomException("The request is rejected because the credentials are invalid");
        }

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails userDetails = userService.loadUserByUsername(loginRequest.getPhoneNumber());
            String token = tokenUtils.generateToken(userDetails);
            return new CustomResponse(new JWTResponse(token, userDetails.getId(), loginRequest.getPhoneNumber(), "Successfully logged"));
        }

        return new CustomResponse("The request is rejected because the credentials are invalid", HttpStatus.BAD_REQUEST.value());
    }

    @PostMapping(value = "/register")
    public Object Register(@RequestBody @Valid User user) {
        return new CustomResponse(userService.register(user));
    }

}
// https://stackoverflow.com/questions/29534109/how-to-log-any-changes-on-entity-automatically