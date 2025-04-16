package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.dto.AccountUpdateForm;
import com.daniilkhanukov.spring.pizza_website.dto.UserRegistrationDTO;
import com.daniilkhanukov.spring.pizza_website.entity.User;
import com.daniilkhanukov.spring.pizza_website.service.RegistrationResult;
import com.daniilkhanukov.spring.pizza_website.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    private final UserServiceImpl userService;

    @Autowired
    public AccountController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String account(Model model, Principal principal) {
        String email = principal.getName();
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            AccountUpdateForm form = new AccountUpdateForm();
            form.setPhone(user.getPhone());
            form.setUserAddress(user.getUserAddress());
            model.addAttribute("user", user);
            model.addAttribute("form", form);
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }
        return "account";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") @Valid UserRegistrationDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        RegistrationResult result = userService.registerUser(user);
        if (!result.getSuccessStatus()) {
            model.addAttribute("error", result.getMessage());
            return "register"; // Возвращаем страницу регистрации с сообщением об ошибке
        }
        return "redirect:/login"; // Успешная регистрация — перенаправляем на страницу входа
    }

    @PostMapping("/account/update")
    public String updateAccountDetails(@ModelAttribute("form") @Valid AccountUpdateForm form,
                                       BindingResult bindingResult,
//                                        @RequestParam("phone") String phone,
//                                       @RequestParam("userAddress") String userAddress,
                                       Principal principal,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            // Если ошибки валидации, переходим обратно на страницу профиля с сообщениями об ошибках
            model.addAttribute("form", form);
            return "account";
        }
        String email = principal.getName();
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPhone(form.getPhone());
            user.setUserAddress(form.getUserAddress());
            userService.update(user);
            model.addAttribute("user", user);
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
        return "redirect:/account";
    }
}
