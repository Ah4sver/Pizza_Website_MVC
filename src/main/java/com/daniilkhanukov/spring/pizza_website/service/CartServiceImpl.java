package com.daniilkhanukov.spring.pizza_website.service;

import com.daniilkhanukov.spring.pizza_website.entity.Cart;
import com.daniilkhanukov.spring.pizza_website.entity.CartItem;
import com.daniilkhanukov.spring.pizza_website.entity.Pizza;
import com.daniilkhanukov.spring.pizza_website.entity.User;
import com.daniilkhanukov.spring.pizza_website.repository.CartRepository;
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
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
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

    @Override
    public Cart addItemToCart(Integer userId, Pizza pizza, int quantity) {
        Cart cart = cartRepository.findFirstByUserIdOrderByIdDesc(userId);
        if (cart == null) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                User userEntity = user.get();
                Cart newCart = new Cart();
                newCart.setUser(userEntity);
                newCart.setItems(new ArrayList<>());
                newCart.setTotalCost(0.0);
                return cartRepository.save(newCart);
            }
            throw new RuntimeException("Корзина для данного пользователя не была найдена. Id пользователя: " + userId);
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
    public void increaseQuantity(Integer userId, Integer pizzaId) {
        Cart cart = findByUserId(userId); // Находим корзину
        cart.increaseQuantity(pizzaId); // Вызываем метод из Cart для увеличения
        cartRepository.save(cart); // Сохраняем изменения в базе
    }

    @Override
    public void decreaseQuantity(Integer userId, Integer pizzaId) {
        Cart cart = findByUserId(userId); // Находим корзину
        cart.decreaseQuantity(pizzaId); // Вызываем метод из Cart для уменьшения
        cartRepository.save(cart); // Сохраняем изменения в базе
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

    public Cart getCurrentCartForUser(Integer userId) {
        Cart cart = cartRepository.findFirstByUserIdOrderByIdDesc(userId);
        if (cart == null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Пользователь с id " + userId + " не найден"));
        }
        return cart;
    }

    public void clearCartById(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart with id " + cartId + " not found"));
        cart.getItems().clear();
        cart.setTotalCost(0.0);
        cartRepository.save(cart);
    }
}
