package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.repository.query.Param;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN') or #user.id == null")
	public void saveUser(@Param("user") User user) {
		user.setRoles(user.getRoles()
    	.stream()
    	.map(role -> {
    		if(role.getId() == null) {
    			role = roleRepository.findByName(role.getName());
    		}
    		return role;
    	})
    	.collect(Collectors.toList())
    	);
		if(user.getId() != null) {
			String password = userRepository.getOne(user.getId()).getPassword();
			user.setPassword(password);
		}
		userRepository.save(user);
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

//	@PreAuthorize("#username == authentication.name or hasRole('ROLE_ADMIN')")
	public User findByUsername(@Param("username") String username){
		return userRepository.findByUsername(username);
	}
}
