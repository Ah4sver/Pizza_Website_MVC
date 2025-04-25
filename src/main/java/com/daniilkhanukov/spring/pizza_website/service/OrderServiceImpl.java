package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.CartItem;
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
//        Cart original = cartRepository.findByUserId(userId);
//        if (original == null || original.getItems().isEmpty()) {
//            throw new RuntimeException("Корзина пуста или не найдена");
//        }
//
//        // 2. Клонируем корзину
//        Cart snapshot = new Cart();
//        snapshot.setUser(original.getUser());
//        snapshot.setTotalCost(original.getTotalCost());
//        for (CartItem item : original.getItems()) {
//            CartItem copy = new CartItem();
//            copy.setPizza(item.getPizza());
//            copy.setQuantity(item.getQuantity());
//            copy.setCart(snapshot);
//            snapshot.getItems().add(copy);
//        }
//        cartRepository.save(snapshot);
//
//
//        Order order = new Order(deliveryAddress, snapshot, original.getUser());
//        orderRepository.save(order);
//
//
//        original.getItems().clear();
//        original.setTotalCost(0.0);
//        cartRepository.save(original);
//
//        return order;
//    }

    @Override
    public void clearCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalCost(0.0);
            cartRepository.save(cart);
        }

    }
}
