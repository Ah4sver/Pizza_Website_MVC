package com.daniilkhanukov.spring.pizza_website.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();;

    @Column(name = "total_cost")
    private double totalCost = 0.0;

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

//    public void addCartItemToCart(CartItem cartItem) {
//        if (items == null) {
//            items = new ArrayList<>();
//        }
//        items.add(cartItem);
//        cartItem.setCart(this);
//        recalculateTotalCost();
//    }
//
//    public void removeCartItemFromCart(CartItem cartItem) {
//        if (items != null) {
//            items.remove(cartItem);
//            cartItem.setCart(null);
//            recalculateTotalCost();
//        }
//    }
//
//    public void recalculateTotalCost() {
//        totalCost = items.stream()
//                .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
//                .sum();
//    }

    public void addItem(CartItem item) {
        if (items == null){
            items = new ArrayList<>();
        }
        for (CartItem existingItem : items) {
            if (existingItem.getPizza().getId().equals(item.getPizza().getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                recalculateTotalCost();
                return;
            }
        }
        item.setCart(this);
        items.add(item);
        recalculateTotalCost();
    }

    public void removeItem(Integer pizzaId) {
        if (items == null) return;
        for (CartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1); // Уменьшаем количество на 1
                } else {
                    items.remove(item); // Если количество было 1, удаляем элемент
                }
                recalculateTotalCost(); // Пересчитываем общую стоимость
                return; // Выходим после обработки
            }
        }
    }

    // Метод для увеличения количества пиццы
    public void increaseQuantity(Integer pizzaId) {
        if (items == null) return;
        for (CartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                item.setQuantity(item.getQuantity() + 1);
                recalculateTotalCost();
                return;
            }
        }
    }

    // Метод для уменьшения количества пиццы
//    public void decreaseQuantity(Integer pizzaId) {
//        if (items == null) return;
//        for (CartItem item : items) {
//            if (item.getPizza().getId().equals(pizzaId)) {
//                if (item.getQuantity() > 1) {
//                    item.setQuantity(item.getQuantity() - 1);
//                } else {
//                    items.remove(item); // Удаляем пиццу, если количество становится 0
//                }
//                recalculateTotalCost();
//                return;
//            }
//        }
//    }

    public void decreaseQuantity(Integer pizzaId) {
        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getPizza().getId().equals(pizzaId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    iterator.remove(); // Удаляем элемент из списка
                }
                recalculateTotalCost();
                return;
            }
        }
    }

    public void recalculateTotalCost() {
        if (items == null) {
            totalCost = 0.0;
        } else {
            totalCost = items.stream()
                    .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                    .sum();
        }
    }
}
