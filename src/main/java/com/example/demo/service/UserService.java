package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public void saveUser(User user) {
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
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public Optional<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
}
