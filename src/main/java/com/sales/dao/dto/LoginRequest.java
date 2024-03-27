package com.sales.dao.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginRequest {
    @NotNull(message = "phone number must not be empty")
    @Pattern(regexp="^[0-9]{9,15}$", message="Phone number must contain only numeric characters and be between 9 and 15 digits")
    private String phoneNumber;
    @NotNull(message = "password must not be empty")
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
