package com.example.demo.config.initData;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
@ConditionalOnBean(RolesInitDataRunner.class)
public class UsersInitDataRunner implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args){
        System.out.println("init users data...");// TODO: replace with logger

        Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER.getName());
        Role roleAdmin = roleRepository.findByName(RoleEnum.ROLE_ADMIN.getName());

        User user = new User("user", passwordEncoder.encode("password1"), true);
        user.setRoles(Arrays.asList(roleUser));

        userRepository.save(user);

        User admin = new User("admin", passwordEncoder.encode("password2"), true);
        admin.setRoles(Arrays.asList(roleUser, roleAdmin));

        userRepository.save(admin);
    }
}
