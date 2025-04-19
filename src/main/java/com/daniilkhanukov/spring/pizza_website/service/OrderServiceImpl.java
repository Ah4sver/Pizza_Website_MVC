package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.Order;
import com.daniilkhanukov.spring.pizza_website.repository.CartRepository;
import com.daniilkhanukov.spring.pizza_website.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

//    @Override
//    public Order processOrder(Integer userId, String deliveryAddress) {
//        Cart cart = cartRepository.findByUserId(userId);
//        if (cart == null || cart.getItems().isEmpty()) {
//            throw new RuntimeException("Корзина пуста или никогда не была создана");
//        }
//
//        Order order = new Order();
//        order.setDeliveryAddress(deliveryAddress);
//        order.setCart(cart);
//
//        orderRepository.save(order);
//
//        clearCart(userId);
//        return null;
//    }
//
//    @Override
//    public void clearCart(Integer userId) {
//        Cart cart = cartRepository.findByUserId(userId);
//        if (cart != null) {
//            cart.getItems().clear();
//            cart.setTotalCost(0.0);
//            cartRepository.save(cart);
//        }
//
//    }
}
