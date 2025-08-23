package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    Order update(Order order);
    Optional<Order> findById(Integer id);
    Optional<Order> findByIdWithItems(Integer id);
    List<Order> findAll();
    void delete(Order order);
    void clearCart(Integer userId);
}
