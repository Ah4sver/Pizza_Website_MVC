package com.daniilkhanukov.spring.pizza_website.repository;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Integer userId);
}
