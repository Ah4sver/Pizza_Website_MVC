package com.daniilkhanukov.spring.pizza_website.repository;

import com.daniilkhanukov.spring.pizza_website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
