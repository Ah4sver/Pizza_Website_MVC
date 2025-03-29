package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import com.daniilkhanukov.spring.pizza_website.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @Override
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public Pizza update(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id);
    }

    @Override
    public Optional<Pizza> findByName(String name) {
        return pizzaRepository.findByName(name);
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public void delete(Pizza pizza) {
        pizzaRepository.delete(pizza);
    }

    @Override
    public void deleteById(Integer id) {
        pizzaRepository.deleteById(id);
    }

    @Override
    public void toggleActiveStatus(Integer pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza not found with id: " + pizzaId));
        pizza.setAvailability(!pizza.isAvailability());
        pizzaRepository.save(pizza);
    }
}
