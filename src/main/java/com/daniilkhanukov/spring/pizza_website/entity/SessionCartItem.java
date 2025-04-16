package com.daniilkhanukov.spring.pizza_website.entity;

public class SessionCartItem {
    private Pizza pizza;
    private int quantity;

    public SessionCartItem(Pizza pizza, int quantity) {
        this.pizza = pizza;
        this.quantity = quantity;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
