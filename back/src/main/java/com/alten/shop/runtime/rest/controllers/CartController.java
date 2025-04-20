package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.CartItem;
import com.alten.shop.domain.services.CartService;
import com.alten.shop.runtime.rest.dtos.CartItemDTO;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/content")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CartItem>> getCartContent(Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.getCartContent(authentication, pageable));
    }

    @PostMapping
    public ResponseEntity<Page<CartItem>> addProductToCart(@RequestBody CartItemDTO cartItem, Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.addProductToCart(cartItem, authentication, pageable));
    }

    @PutMapping("/remove")
    public ResponseEntity<Page<CartItem>> removeProductFromCart(@RequestBody ProductItemDTO product, Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.removeProductToCart(product, authentication, pageable));
    }
}
