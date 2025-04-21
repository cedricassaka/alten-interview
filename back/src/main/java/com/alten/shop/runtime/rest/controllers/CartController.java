package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.Cart;
import com.alten.shop.domain.services.CartService;
import com.alten.shop.runtime.rest.dtos.CartItemDTO;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<Cart> getCartContent(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication));
    }

    @PostMapping
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartItemDTO cartItem, Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.addProductToCart(cartItem, authentication));
    }

    @PutMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestBody ProductItemDTO product, Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.removeProductToCart(product, authentication));
    }
}
