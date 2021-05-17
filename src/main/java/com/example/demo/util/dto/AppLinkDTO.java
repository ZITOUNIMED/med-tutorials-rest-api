package com.example.demo.util.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppLinkDTO implements Serializable {
    private String value;
    private String link;
}
