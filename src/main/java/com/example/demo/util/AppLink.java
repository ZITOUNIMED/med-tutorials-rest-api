package com.example.demo.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppLink implements Serializable {
    private String value;
    private String link;
}
