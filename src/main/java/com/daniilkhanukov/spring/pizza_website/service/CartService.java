package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import com.daniilkhanukov.spring.pizza_website.entity.SessionCart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart save(Cart cart);
    Cart update(Cart cart);
    Optional<Cart> findById(Integer id);
    List<Cart> findAll();
    void delete(Cart cart);
    Cart findByUserId(Integer userId);
    Cart addItemToCart(Integer userId, Pizza pizza, int quantity);
    Cart removeItemFromCart(Integer userId, Integer cartItemId);
//    void clearCart(Integer userId);
    void increaseQuantity(Integer userId, Integer pizzaId);
    void decreaseQuantity(Integer userId, Integer pizzaId);
    Cart getCurrentCartForUser(Integer userId);
    void mergeSessionCart(Integer id, SessionCart sessionCart);
}
