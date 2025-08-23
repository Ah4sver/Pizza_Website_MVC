package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.security.CustomAuthenticationSuccessHandler;
import com.daniilkhanukov.spring.pizza_website.dto.AccountUpdateForm;
import com.daniilkhanukov.spring.pizza_website.dto.UserRegistrationDTO;
import com.daniilkhanukov.spring.pizza_website.entity.User;
import com.daniilkhanukov.spring.pizza_website.service.RegistrationResult;
import com.daniilkhanukov.spring.pizza_website.service.UserServiceImpl;
import com.daniilkhanukov.spring.pizza_website.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    private final UserServiceImpl userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @Autowired
    public AccountController(UserServiceImpl userService,
                             CustomUserDetailsService customUserDetailsService,
                             CustomAuthenticationSuccessHandler successHandler) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    // Отображение страницы аккаунта пользователя с данными
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

    // Отображение страницы входа
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Отображение страницы с формой регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "register";
    }

    // Обработка регистрации пользователя с валидацией, автоматическим логином и переносом сессионной корзины
    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") @Valid UserRegistrationDTO userDTO,
                           BindingResult bindingResult,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response) {

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
            return "register";
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        
        securityContextRepository.saveContext(context, request, response);

        successHandler.transferSessionCartToUserCart(request, authentication);

        return "redirect:/pizza";

    }

    // Обработка обновления данных аккаунта (телефон и адрес)
    @PostMapping("/account/update")
    public String updateAccountDetails(@ModelAttribute("form") @Valid AccountUpdateForm form,
                                       BindingResult bindingResult,
                                       Principal principal,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "account";
        }
        String email = principal.getName();
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                model.addAttribute("form", form);
                return "account";
            }

            if (form.getPhone() != null && !form.getPhone().isBlank()) {
                user.setPhone(form.getPhone());
            }
            if (form.getUserAddress() != null && !form.getUserAddress().isBlank()) {
                user.setUserAddress(form.getUserAddress());
            }

            userService.update(user);
            model.addAttribute("user", user);
        } else {
            throw new RuntimeException("Пользователь не найден");
        }
        return "redirect:/account";
    }
}
