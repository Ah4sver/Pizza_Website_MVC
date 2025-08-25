package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.entity.*;
import com.daniilkhanukov.spring.pizza_website.service.CartServiceImpl;
import com.daniilkhanukov.spring.pizza_website.service.OrderServiceImpl;
import com.daniilkhanukov.spring.pizza_website.service.PizzaServiceImpl;
import com.daniilkhanukov.spring.pizza_website.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CartController {
    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final PizzaServiceImpl pizzaService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public CartController(CartServiceImpl cartService, OrderServiceImpl orderService, PizzaServiceImpl pizzaService, UserServiceImpl userServiceImpl) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.pizzaService = pizzaService;
        this.userServiceImpl = userServiceImpl;
    }

    // Получение или создание сессионной корзины для анонимных пользователей
    private SessionCart getSessionCart(HttpSession session) {
        SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
        if (sessionCart == null) {
            sessionCart = new SessionCart();
            session.setAttribute("sessionCart", sessionCart);
        }
        return sessionCart;
    }

    // Просмотр корзины для анонимных пользователей
    @GetMapping("/cart/anonymous")
    public String viewAnonymousCart(Model model, HttpSession session) {
        SessionCart sessionCart = getSessionCart(session);
        model.addAttribute("sessionCart", sessionCart);
        return "anonCart";
    }

    // Просмотр корзины для авторизованных пользователей
    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/cart/anonymous";
        }
            User user = getCurrentUser(principal);
            Cart cart = null;
            if (cartService.getCurrentCartForUser(user.getId()) == null) {
                cart = new Cart();
            } else {
                cart = cartService.getCurrentCartForUser(user.getId());
            }
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
        return "cart";
    }

    // Удаление пиццы из анонимной корзины
    @GetMapping("/cart/anonymous/remove/{pizzaId}")
    public String removeFromAnonymousCart(@PathVariable Integer pizzaId, HttpSession session) {
        SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
        if (sessionCart != null) {
            sessionCart.removeItem(pizzaId);
        }
        return "redirect:/cart/anonymous";
    }
    // Метод для увеличения количества пиццы
    @GetMapping("/cart/anonymous/increase/{pizzaId}")
    public String increaseAnonymousQuantity(@PathVariable Integer pizzaId, HttpSession session) {
        SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
        if (sessionCart != null) {
            sessionCart.increaseAnonymousQuantity(pizzaId);
        }
        return "redirect:/cart/anonymous";
    }

    // Метод для уменьшения количества пиццы
    @GetMapping("/cart/anonymous/decrease/{pizzaId}")
    public String decreaseAnonymousQuantity(@PathVariable Integer pizzaId, HttpSession session) {
        SessionCart sessionCart = (SessionCart) session.getAttribute("sessionCart");
        if (sessionCart != null) {
            sessionCart.decreaseAnonymousQuantity(pizzaId);
        }
        return "redirect:/cart/anonymous";
    }

    // Удаление товара из корзины авторизованного пользователя
    @GetMapping("/cart/remove/{pizzaId}")
    public String removeFromCart(@PathVariable Integer pizzaId, Principal principal) {
        if (principal == null) {
            return "redirect:/cart/anonymous/remove/" + pizzaId;
        }
        User user = getCurrentUser(principal);
        cartService.removeItemFromCart(user.getId(), pizzaId);
        return "redirect:/cart";
    }

    // Увеличение количества в корзине авторизованного пользователя
    @GetMapping("/cart/increase/{pizzaId}")
    public String increaseQuantity(@PathVariable Integer pizzaId, Principal principal) {
        if (principal == null) {
            return "redirect:/cart/anonymous/increase/" + pizzaId;
        }
        User user = getCurrentUser(principal);
        cartService.increaseQuantity(user.getId(), pizzaId);
        return "redirect:/cart";
    }

    // Уменьшение количества в корзине авторизованного пользователя
    @GetMapping("/cart/decrease/{pizzaId}")
    public String decreaseQuantity(@PathVariable Integer pizzaId, Principal principal) {
        if (principal == null) {
            return "redirect:/cart/anonymous/decrease/" + pizzaId;
        }
        User user = getCurrentUser(principal);
        cartService.decreaseQuantity(user.getId(), pizzaId);
        return "redirect:/cart";
    }

    // Добавление в корзину
    @GetMapping("/cart/add/{pizzaId}")
    public String addToCart(@PathVariable Integer pizzaId, HttpSession session, Principal principal) {
        Optional<Pizza> pizzaOptional = pizzaService.findById(pizzaId);
        if (!pizzaOptional.isPresent()) {
            return "redirect:/pizza?error=Pizza+not+found";
        }
        Pizza pizza = pizzaOptional.get();

        if (principal != null) {
            // Авторизованный пользователь
            User user = getCurrentUser(principal);
            cartService.addItemToCart(user.getId(), pizza, 1);
        } else {
            // Анонимный пользователь
            SessionCart sessionCart = getSessionCart(session);
            SessionCartItem item = new SessionCartItem(pizza, 1);
            sessionCart.addItem(item);
        }
        return "redirect:/pizza";
    }

    @GetMapping("/order/success")
    public String orderSuccess() {
        return "order-success";
    }

    // Создание заказа
    @PostMapping("/cart/order")
    public String createOrder(@RequestParam String deliveryAddress, Principal principal, Model model) {
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            model.addAttribute("error", "Пожалуйста, введите адрес доставки заказа");
            User user = getCurrentUser(principal);
            Cart cart = cartService.getCurrentCartForUser(user.getId());
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
            return "cart";
        }
        User user = getCurrentUser(principal);
        Cart cart = cartService.getCurrentCartForUser(user.getId());
        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("error", "Ваша корзина пуста \n Время выбрать любимую пиццу!");
            return "cart";
        }
        orderService.createOrder(user.getId(), deliveryAddress);
        orderService.clearCart(user.getId());

        return "redirect:/order/success";
    }

    // Получение текущего пользователя
    private User getCurrentUser(Principal principal) {
        String email = principal.getName();
        Optional<User> optionalUser = userServiceImpl.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Данного пользователя нет");
        }
        User user = optionalUser.get();
        return user;
    }


}
