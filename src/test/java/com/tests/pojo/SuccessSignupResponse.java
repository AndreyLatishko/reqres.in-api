package com.tests.pojo;

public class SuccessSignupResponse {
    private Integer id;
    private String token;


    public SuccessSignupResponse(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public SuccessSignupResponse() {
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
