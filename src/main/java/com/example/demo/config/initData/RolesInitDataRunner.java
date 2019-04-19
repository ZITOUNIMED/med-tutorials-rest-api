package com.example.demo.config.initData;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value="init.roles.data")
public class RolesInitDataRunner implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args){
        System.out.println("init roles data...");// TODO: replace with logger
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);

        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
    }
}
