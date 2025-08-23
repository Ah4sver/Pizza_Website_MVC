package com.daniilkhanukov.spring.pizza_website.repository;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Пришлось использовать @Query для решения проблемы N+1
    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.items i " +
            "LEFT JOIN FETCH i.pizza " +
            "WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithDetails(@Param("userId") Integer userId);
//    Cart findByUserId(Integer userId);
//    Cart findFirstByUserIdOrderByIdDesc(Integer userId);
}
