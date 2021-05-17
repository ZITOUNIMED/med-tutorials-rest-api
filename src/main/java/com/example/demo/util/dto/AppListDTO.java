package com.example.demo.util.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppListDTO implements Serializable {
    private String title;
    private List<String> items;
}
