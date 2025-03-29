package com.daniilkhanukov.spring.pizza_website.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartItem> items;

    @Column(name = "total_cost")
    private double totalCost;

    public Cart(){
    }

    public Cart(User user, List<CartItem> items, double totalCost) {
        this.user = user;
        this.items = items;
        this.totalCost = totalCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void addCartItemToCart(CartItem cartItem) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(cartItem);
        cartItem.setCart(this);
        recalculateTotalCost();
    }

    public void removeCartItemFromCart(CartItem cartItem) {
        if (items != null) {
            items.remove(cartItem);
            cartItem.setCart(null);
            recalculateTotalCost();
        }
    }

    public void recalculateTotalCost() {
        totalCost = items.stream()
                .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                .sum();
    }
}
