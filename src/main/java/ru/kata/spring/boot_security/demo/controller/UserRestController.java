package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.ServiceRole;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserRestController {

    private final ServiceRole roleService;
    private final ServiceUser userService;

    public UserRestController(ServiceRole roleService, ServiceUser userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/users/{id}")
    public ResponseEntity<User> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id).get());
    }

    @GetMapping(value = "/users/get/{email}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.findByName(email).get());
    }

    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.allUsers());
    }

    @PostMapping(value = "/admin/add")
    public ResponseEntity<User> addUser(@ModelAttribute User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoleSet()) {
            roles.add(roleService.findByName(role.getRole()).get());
        }
        user.setRoleSet(roles);
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @PostMapping(value = "/admin/update")
    public ResponseEntity<User> updateUser(@ModelAttribute User user, @RequestParam Long[] roleSet) {
        user.setRoleSet(roleService.getRolesByIds(roleSet));
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/admin/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> userPage(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id).get());
    }
}