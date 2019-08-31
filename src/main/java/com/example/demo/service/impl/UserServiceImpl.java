package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.data.repository.query.Param;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@PreAuthorize("#username == authentication.name or hasRole('ROLE_ADMIN')")
	public User findByUsername(@Param("username") String username){
		return userRepository.findByUsername(username);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN') or #user.id == null")
	public void save(User user) {
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

	@Override
	public void delete(User user) {
		// TODO
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findById(Long id) {
		throw new RuntimeException("findById method is not implemented yet!");
	}
	
	@Override
	public void saveAll(List<User> list) {
		throw new RuntimeException("saveAll method is not implemented yet!");
	}
}
