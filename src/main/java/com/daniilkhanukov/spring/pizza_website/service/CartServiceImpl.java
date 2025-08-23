package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.*;
import com.daniilkhanukov.spring.pizza_website.repository.CartRepository;
import com.daniilkhanukov.spring.pizza_website.repository.PizzaRepository;
import com.daniilkhanukov.spring.pizza_website.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, PizzaRepository pizzaRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.pizzaRepository = pizzaRepository;
    }

    private Cart getOrCreateCartForUser(Integer userId) {
        return cartRepository.findByUserIdWithDetails(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("Пользователь не найден. Id: " + userId));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
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
        return getOrCreateCartForUser(userId);
    }

    @Transactional
    public Cart cloneCartForOrder(Cart original, User user) {
        Cart clone = new Cart();
        clone.setTotalCost(original.getTotalCost());
        clone.setUser(user);

        List<CartItem> itemsCopy = new ArrayList<>();
        for (CartItem i : original.getItems()) {
            CartItem copy = new CartItem();
            copy.setPizza(i.getPizza());
            copy.setQuantity(i.getQuantity());
            copy.setCart(clone);
            itemsCopy.add(copy);
        }
        clone.setItems(itemsCopy);
        return cartRepository.save(clone);
    }

    @Override
    public Cart addItemToCart(Integer userId, Pizza pizza, int quantity) {
        Cart cart = getOrCreateCartForUser(userId);

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
    public Cart removeItemFromCart(Integer userId, Integer pizzaId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.getItems().removeIf(item -> Objects.equals(item.getPizza().getId(), pizzaId));
        cart.recalculateTotalCost();
        return cartRepository.save(cart);
    }

    @Override
    public void increaseQuantity(Integer userId, Integer pizzaId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.increaseQuantity(pizzaId);
        cartRepository.save(cart);
    }

    @Override
    public void decreaseQuantity(Integer userId, Integer pizzaId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.decreaseQuantity(pizzaId);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCurrentCartForUser(Integer userId) {
        return getOrCreateCartForUser(userId);
    }

    @Override
    @Transactional
    public void mergeSessionCart(Integer userId, SessionCart sessionCart) {

        Cart userCart = getOrCreateCartForUser(userId);

        for (SessionCartItem sessionItem : sessionCart.getItems()) {
            Pizza pizzaFromSession = sessionItem.getPizza();

            Optional<CartItem> existingItemOpt = userCart.getItems().stream()
                    .filter(item -> Objects.equals(item.getPizza().getId(), pizzaFromSession.getId()))
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                CartItem existingItem = existingItemOpt.get();
                existingItem.setQuantity(existingItem.getQuantity() + sessionItem.getQuantity());
            } else {
                CartItem newItem = new CartItem();

                Pizza managedPizza = pizzaRepository.findById(pizzaFromSession.getId())
                        .orElseThrow(() -> new RuntimeException("Пицца не найдена"));
                newItem.setPizza(managedPizza);
                newItem.setQuantity(sessionItem.getQuantity());
                newItem.setCart(userCart);
                userCart.getItems().add(newItem);
            }
        }
        userCart.recalculateTotalCost();

        cartRepository.save(userCart);
    }

    public void clearCartById(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart with id " + cartId + " not found"));
        cart.getItems().clear();
        cart.setTotalCost(0.0);
        cartRepository.save(cart);
    }
}
