package com.daniilkhanukov.spring.pizza_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.daniilkhanukov.spring.pizza_website.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
