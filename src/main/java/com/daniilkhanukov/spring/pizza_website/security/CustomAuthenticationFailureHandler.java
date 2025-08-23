package com.daniilkhanukov.spring.pizza_website.security;

import com.daniilkhanukov.spring.pizza_website.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("email");
        request.getSession().setAttribute("lastEmail", email);

        String msg = userRepository.findByEmail(email).isPresent()
                ? "Неверный пароль."
                : "Пользователь с такой почтой не найден.";
        request.getSession().setAttribute("loginError", msg);

        response.sendRedirect("/login?error");
    }
}
