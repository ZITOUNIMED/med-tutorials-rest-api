package com.example.demo.web.rest.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String passwordConfirm;
}
