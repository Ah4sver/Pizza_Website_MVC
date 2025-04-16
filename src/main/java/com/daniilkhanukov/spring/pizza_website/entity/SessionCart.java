package com.daniilkhanukov.spring.pizza_website.entity;

import java.util.ArrayList;
import java.util.List;

public class SessionCart {
    private List<SessionCartItem> items = new ArrayList<>();
    private double totalCost = 0.0;

    public List<SessionCartItem> getItems() {
        return items;
    }

    public void setItems(List<SessionCartItem> items) {
        this.items = items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void addItem(SessionCartItem item) {
        for (SessionCartItem existingItem : items) {
            if (existingItem.getPizza().getId().equals(item.getPizza().getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                recalculateTotalCost();
                return;
            }
        }
        items.add(item);
        recalculateTotalCost();
    }

    public void removeItem(Integer pizzaId) {
        for (SessionCartItem item : items) {
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
    public void increaseAnonymousQuantity(Integer pizzaId) {
        for (SessionCartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                item.setQuantity(item.getQuantity() + 1);
                recalculateTotalCost();
                return;
            }
        }
    }

    // Метод для уменьшения количества пиццы
    public void decreaseAnonymousQuantity(Integer pizzaId) {
        for (SessionCartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    items.remove(item); // Удаляем пиццу, если количество становится 0
                }
                recalculateTotalCost();
                return;
            }
        }
    }

    public void recalculateTotalCost() {
        totalCost = items.stream()
                .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                .sum();
    }
}
