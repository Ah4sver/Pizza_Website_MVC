package com.daniilkhanukov.spring.pizza_website.security;

import com.daniilkhanukov.spring.pizza_website.entity.*;
import com.daniilkhanukov.spring.pizza_website.repository.PizzaRepository;
import com.daniilkhanukov.spring.pizza_website.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.daniilkhanukov.spring.pizza_website.service.UserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        transferSessionCartToUserCart(request, authentication);

        setDefaultTargetUrl("/pizza");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public void transferSessionCartToUserCart(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");

        if (sessionCart != null && !sessionCart.getItems().isEmpty()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();

            cartService.mergeSessionCart(user.getId(), sessionCart);

            session.removeAttribute("sessionCart");
        }
    }
}
