package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceRole {
    Set<Role> getRolesByIds(Long[] ids);

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String role);

    List<Role> findAll();

    Role saveRole(Role role);

    void deleteById(Long id);

    Role update(Role role);
}