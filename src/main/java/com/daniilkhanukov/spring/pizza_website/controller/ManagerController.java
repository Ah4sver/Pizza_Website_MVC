package com.daniilkhanukov.spring.pizza_website.controller;

import com.daniilkhanukov.spring.pizza_website.entity.Order;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import com.daniilkhanukov.spring.pizza_website.service.OrderService;
import com.daniilkhanukov.spring.pizza_website.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {
    private final PizzaService pizzaService;
    private final OrderService orderService;

    @Autowired
    public ManagerController(PizzaService pizzaService, OrderService orderService) {
        this.pizzaService = pizzaService;
        this.orderService = orderService;
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

    @PostMapping("/changePrice/{pizzaId}")
    public String changePizzaPrice(@PathVariable Integer pizzaId, @RequestParam("price") Double price) {
        pizzaService.changePrice(pizzaId, price);
        return "redirect:/manager";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "manager_orders"; // Имя JSP представления (manager_orders.jsp)
    }

    @GetMapping("/orders/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Integer orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ с id " + orderId + " не найден"));
        orderService.delete(order);
        return "redirect:/manager/orders";
    }
}
