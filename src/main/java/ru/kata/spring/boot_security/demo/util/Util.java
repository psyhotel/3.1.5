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
        serviceUser.createUser(new User("login", "password", roleSet));
        serviceUser.createUser(new User("user", "password", roleSet));
    }

}