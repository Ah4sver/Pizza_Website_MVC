package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.CartItem;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import com.daniilkhanukov.spring.pizza_website.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public Cart findByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }
    //-----------------------------------------------------------------------------------------------------------------
    //------------------------------------В--О--Т----Т--У--Т----Ч--Е--К--Н--И------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public Cart addItemToCart(Integer userId, Pizza pizza, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user id: " + userId);
        }
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getPizza().getId(), pizza.getId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setPizza(pizza);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cart.recalculateTotalCost();
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(Integer userId, Integer cartItemId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Корзина для данного пользователя не была найдена");
        }
        cart.getItems().removeIf(item -> Objects.equals(item.getId(), cartItemId));
        cart.recalculateTotalCost();
        return cartRepository.save(cart);
    }

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
