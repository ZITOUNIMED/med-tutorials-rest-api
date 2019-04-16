package com.example.demo.web.rest;

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
public class UserController {

    @GetMapping
    public void getAllUsers(){

    }

    @GetMapping("/{id}")
    public void getUserInfo(){

    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){

    }

    @PutMapping
    public void updateUser(){

    }

    @PutMapping("/enable-disable")
    public void enableDisableUser(){

    }

    @DeleteMapping("/{id}")
    public void deleteUser(){

    }
}
