package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.service.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaController {
    private final PizzaServiceImpl pizzaService;

    @Autowired
    public PizzaController(PizzaServiceImpl pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/pizza")
    public String home(Model model) {
        model.addAttribute("pizzas", pizzaService.findAll());
        return "home";
    }
}
