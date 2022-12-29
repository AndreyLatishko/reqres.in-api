package com.tests.pojo;

public class SignupRequest {
    private String email;
    private String password;

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
