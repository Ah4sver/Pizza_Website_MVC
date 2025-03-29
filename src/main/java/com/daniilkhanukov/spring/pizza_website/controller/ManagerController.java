package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {
    private final PizzaService pizzaService;

    @Autowired
    public ManagerController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public String managePizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.findAll());
        return "manager";
    }

    @GetMapping("/toggle/{pizzaId}")
    public String togglePizzaAvailability(@PathVariable Integer pizzaId) {
        pizzaService.toggleActiveStatus(pizzaId);
        return "redirect:/manager";
    }
}
