package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.Cart;
import com.alten.shop.domain.models.CartItem;
import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.services.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class CartController {

    private final CartService cartService;

    @GetMapping("/get-content")
    public ResponseEntity<Page<CartItem>> getCartContent(Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(cartService.getCartContent(authentication, pageable));
    }

    @PostMapping("/add-product")
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartItem cartItem, Authentication authentication) {
        return ResponseEntity.ok(cartService.addProductToCart(cartItem, authentication));
    }

    @PutMapping("/remove-product")
    public ResponseEntity<Cart> removeProductFromCart(@RequestBody Product product, Authentication authentication) {
        return ResponseEntity.ok(cartService.removeProductToCart(product, authentication));
    }
}
