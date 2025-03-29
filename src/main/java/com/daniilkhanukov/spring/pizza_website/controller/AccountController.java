package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.entity.User;
import com.daniilkhanukov.spring.pizza_website.service.RegistrationResult;
import com.daniilkhanukov.spring.pizza_website.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    private final UserServiceImpl userService;

    @Autowired
    public AccountController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        RegistrationResult result = userService.registerUser(user);
        if (!result.getSuccessStatus()) {
            model.addAttribute("error", result.getMessage());
            return "register"; // Возвращаем страницу регистрации с сообщением об ошибке
        }
        return "redirect:/login"; // Успешная регистрация — перенаправляем на страницу входа
    }
}
