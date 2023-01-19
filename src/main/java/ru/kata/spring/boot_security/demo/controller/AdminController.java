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
        model.addAttribute("user", userService.findByName(name).get());
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", roleService.findAll());
        return "/admin";
    }

    @GetMapping("/admin/create-user")
    public String createUserForm(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("user", user);
        model.addAttribute("user", userService.findByName(name).get());
        model.addAttribute("roles", roleService.findAll());
        return "create-user";
    }

    @PostMapping("/admin/create-user")
    public String createUser(@ModelAttribute("new_user") User user, HttpServletRequest request) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/user-delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        return "create-user";
    }

    @PostMapping("/admin/{id}/update")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, HttpServletRequest request) {
        User user1 = user;
        userService.updateUser(user1);
        return "redirect:/admin";
    }
}