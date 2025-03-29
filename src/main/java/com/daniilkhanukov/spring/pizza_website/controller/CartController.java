package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.service.CartServiceImpl;
import com.daniilkhanukov.spring.pizza_website.service.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;

    @Autowired
    public CartController(CartServiceImpl cartService, OrderServiceImpl orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    private Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = getOrCreateCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }


    @GetMapping("/cart/add/{pizzaId}")
    public String addToCart(@PathVariable Integer pizzaId, HttpSession session) {
        Cart cart = getOrCreateCart(session);
        cartService.addItemToCart(cart, pizzaId, 1); // Добавляем 1 пиццу
        return "redirect:/pizza";
    }


}
