package com.example.demo.web.rest;

import com.example.demo.config.security.JwtTokenProvider;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.RoleEnum;
import com.example.demo.web.rest.request.SignInRequest;
import com.example.demo.web.rest.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody SignInRequest request){
        try{
            String username = request.getUsername();
            String password = request.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = this.userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
                    .getRoles()
                    .stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toList());
            String token = jwtTokenProvider.createToken(username, roles);
            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignUpRequest request){
        try{
            String username = request.getUsername();
            String password = request.getPassword();
            String passwordConfirm = request.getPasswordConfirm();

            Optional<User> existingUser = userRepository.findByUsername(username);
            if(existingUser.isPresent()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Username is already user!");
            }

            // a digit must occur at least once
            // a lower case letter must occur at least once
            // an upper case letter must occur at least once
            // a special character must occur at least once
            // no whitespace allowed in the entire string
            // anything, at least 10 places though
            Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)(?=.*[@#$%^&+=]).{10,}$");
            Matcher m = passwordPattern.matcher(password);
            if(!m.matches()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid password!");
            }

            if(!password.equals(passwordConfirm)){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Passwords are different!");
            }

            Role role = roleRepository.findByName(RoleEnum.ROLE_USER.getName());

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(Arrays.asList(role));
            user.setEnable(true);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid cridentials!");
        }
    }
}
