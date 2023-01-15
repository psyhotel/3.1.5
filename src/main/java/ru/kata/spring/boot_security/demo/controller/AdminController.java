package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.ServiceRole;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
public class AdminController {

    private final ServiceUser userService;

    private final ServiceRole roleService;

    @Autowired
    public AdminController(ServiceUser userService, ServiceRole roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("username", name);
        return "/admin";
    }

    @GetMapping("/admin/users")
    public String findAll(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "users";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(User user, Model model) {
        model.addAttribute("user", user);
        return "create-user";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute("new_user") User user, HttpServletRequest request) {
        user.setRoleSet(Collections.singleton(roleService.findById(Long.valueOf(request.getParameter("role"))).get()));
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        User user1 = user;
        user1.setRoleSet(Collections.singleton(roleService.findById(Long.valueOf(request.getParameter("role"))).get()));
        userService.updateUser(user1);
        return "redirect:/admin/users";
    }
}