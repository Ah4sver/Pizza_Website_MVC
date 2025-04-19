package com.daniilkhanukov.spring.pizza_website.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(String deliveryAddress, Cart cart, User user) {
        this.deliveryAddress = deliveryAddress;
        this.cart = cart;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String pizzasSummary = cart.getItems().stream()
                .map(item -> item.getPizza().getName() + " x" + item.getQuantity())
                .collect(Collectors.joining(", "));

        return "Order{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "anonymous") +
                ", address='" + deliveryAddress + '\'' +
                ", items=[" + pizzasSummary + "]" +
                '}';
    }
}
