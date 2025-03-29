package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(User user);
    Optional<User> findById(Integer id);
    List<User> findAll();
    void delete(User user);
    void deleteById(Integer id);
    RegistrationResult registerUser(User user);
}
