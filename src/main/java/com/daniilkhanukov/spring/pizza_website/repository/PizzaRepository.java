package com.daniilkhanukov.spring.pizza_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    Optional<Pizza> findByName(String name);
}
