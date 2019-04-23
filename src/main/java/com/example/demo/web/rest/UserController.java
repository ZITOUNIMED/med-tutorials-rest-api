package com.example.demo.web.rest;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
    	List<User> users = userService.getUsers()
    			.stream()
    			.map(user -> {
    				user.setPassword(null);
    				return user;
    			})
    			.collect(Collectors.toList());
    	return ResponseEntity.ok(users);
    }

    @GetMapping("/by-username/{username}")
	public ResponseEntity<User> findByUsername(@PathVariable String username){
    	User user = userService.findByUsername(username).orElse(null);
    	if(user!=null){
    		user.setPassword(null); // Don't return password in the response
		}
    	return ResponseEntity.ok(user);
	}
    
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody User user){
    	userService.saveUser(user);
    	return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
    	userService.deleteUser(id);
    	return ResponseEntity.accepted().build();
    }
}
