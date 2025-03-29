package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;

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
    void clearCart(Integer userId);
}
