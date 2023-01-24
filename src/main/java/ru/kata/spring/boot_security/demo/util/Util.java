package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.ServiceRole;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    @Autowired
    ServiceUser serviceUser;

    @Autowired
    ServiceRole serviceRole;

    Set<Role> roleSet = new HashSet<>();

    @Transactional
    public void generateUsers() {
        roleSet.add(serviceRole.saveRole(new Role("ROLE_USER")));
        roleSet.add(serviceRole.saveRole(new Role("ROLE_ADMIN")));
        //password: password
        serviceUser.createUser(new User(1L, "login", "loginov", "$2a$12$uldC7GclBLFrPxToan7IdehSYxtw6PfgeL/Q5a6x346ekNc9hC98G", 1, "login@mail.com" ,roleSet));
        serviceUser.createUser(new User(2L, "user", "userov", "$2a$12$uldC7GclBLFrPxToan7IdehSYxtw6PfgeL/Q5a6x346ekNc9hC98G", 1, "user@mail.com" ,roleSet));
    }
}