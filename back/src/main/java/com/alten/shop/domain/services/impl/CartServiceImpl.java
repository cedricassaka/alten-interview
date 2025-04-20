package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.models.*;
import com.alten.shop.domain.services.CartService;
import com.alten.shop.domain.services.ProductService;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.handler.exception.InvalidResourcePropertyException;
import com.alten.shop.runtime.handler.exception.ResourceNotFoundException;
import com.alten.shop.runtime.repositories.CartItemRepository;
import com.alten.shop.runtime.repositories.CartRepository;
import com.alten.shop.runtime.rest.dtos.CartItemDTO;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
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
    private final ProductService productService;

    @Override
    @Transactional
    public Page<CartItem> addProductToCart(CartItemDTO cartItem, Authentication authentication, Pageable pageable) {
        Cart cart = getCart(authentication);
        Optional<CartItem> itemExist = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), cartItem.product().id()))
                .findFirst();
        Product product = productService.findById(cartItem.product().id());
        if (itemExist.isPresent()) {
            CartItem item = itemExist.get();
            if (cartItem.quantity() > product.getQuantity()) throw new InvalidResourcePropertyException("CartItem Quantity");
            cart.getItems().remove(item);
            item.setQuantity(cartItem.quantity());
            cart.getItems().add(item);
        } else {
            CartItem item = CartItem.builder()
                    .id(CartItemKey.builder()
                            .productId(cartItem.product().id())
                            .cartId(cart.getId())
                            .build())
                    .quantity(cartItem.quantity())
                    .product(product)
                    .cart(cart)
                    .build();

            itemRepository.save(item);
        }
        return getCartContent(authentication, pageable);
    }

    @Override
    @Transactional
    public Page<CartItem> removeProductToCart(ProductItemDTO product, Authentication authentication, Pageable pageable) {
        Cart cart = getCart(authentication);
        log.info("{}", cart.getItems().size());
        Optional<CartItem> itemExist = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getProduct().getId(), product.id()))
                .findFirst();
        if (cart.getItems().isEmpty() || itemExist.isEmpty()) throw new ResourceNotFoundException("Product not found in cart");
        CartItem existingItem = itemExist.get();
        cart.getItems().remove(existingItem);
        itemRepository.delete(existingItem);
        return getCartContent(authentication, pageable);
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
