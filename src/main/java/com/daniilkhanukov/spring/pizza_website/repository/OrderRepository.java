package com.daniilkhanukov.spring.pizza_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.daniilkhanukov.spring.pizza_website.entity.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Пришлось использовать @Query для решения проблемы N+1
    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.user " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.pizza")
    List<Order> findAllWithDetails();

    // Пришлось использовать @Query для решения проблемы N+1
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.pizza " +
            "WHERE o.id = :orderId")
    Optional<Order> findByIdWithDetails(@Param("orderId") Integer orderId);
}
