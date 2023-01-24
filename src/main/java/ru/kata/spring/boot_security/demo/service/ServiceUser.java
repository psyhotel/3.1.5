package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface ServiceUser {
    Optional<User> findById(Long id);

    List<User> allUsers();

    Optional<User> findByName(String name);

    User createUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);
}