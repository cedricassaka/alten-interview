package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.models.*;
import com.alten.shop.domain.services.CartService;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.handler.exception.InvalidResourcePropertyException;
import com.alten.shop.runtime.handler.exception.ResourceNotFoundException;
import com.alten.shop.runtime.repositories.CartItemRepository;
import com.alten.shop.runtime.repositories.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final CartItemRepository itemRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Cart addProductToCart(CartItem cartItem, Authentication authentication) {
        Cart cart = getCart(authentication);
        Optional<CartItem> itemExist = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), cartItem.getProduct().getId()))
                .findFirst();
        if (itemExist.isPresent()) {
            CartItem item = itemExist.get();
            if (cartItem.getQuantity() > cartItem.getProduct().getQuantity()) throw new InvalidResourcePropertyException("CartIem Quantity");
            cart.getItems().remove(item);
            item.setQuantity(cartItem.getQuantity());
            cart.getItems().add(item);
        } else {
            cartItem.setId(
                    CartItemKey.builder()
                            .productId(cartItem.getProduct().getId())
                            .cartId(cart.getId())
                            .build()
            );
            cartItem.setCart(cart);
            itemRepository.save(cartItem);

            cart.getItems().add(cartItem);
        }
        return repository.save(cart);
    }

    @Override
    @Transactional
    public Cart removeProductToCart(Product product, Authentication authentication) {
        Cart cart = getCart(authentication);
        log.info("{}", cart.getItems());
        Optional<CartItem> itemExist = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), product.getId()))
                .findFirst();
        if (cart.getItems().isEmpty() || itemExist.isEmpty()) throw new ResourceNotFoundException("Product not found in cart");
        CartItem existingItem = itemExist.get();
        cart.getItems().remove(existingItem);
        itemRepository.delete(existingItem);
        return repository.save(cart);
    }

    @Transactional
    private Cart getCart(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return repository.findFirstByUserId(user.getId())
                .orElse(
                        repository.save(
                            Cart
                                .builder()
                                .user(user)
                                .items(new HashSet<>())
                                .build()
                        )
                );
    }

    @Override
    public Page<CartItem> getCartContent(Authentication authentication, Pageable pageable) {
        return itemRepository.findByCartId(getCart(authentication).getId(), pageable);
    }
}
