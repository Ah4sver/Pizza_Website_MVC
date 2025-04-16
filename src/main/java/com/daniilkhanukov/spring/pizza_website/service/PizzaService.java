package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import java.util.List;
import java.util.Optional;

public interface PizzaService {
    Pizza save(Pizza pizza);
    Pizza update(Pizza pizza);
    Optional<Pizza> findById(Integer id);
    Optional<Pizza> findByName(String name);
    List<Pizza> findAll();
    void delete(Pizza pizza);
    void deleteById(Integer id);
    void toggleActiveStatus(Integer pizzaId);
    void changePrice(Integer pizzaId, Double price);
}
