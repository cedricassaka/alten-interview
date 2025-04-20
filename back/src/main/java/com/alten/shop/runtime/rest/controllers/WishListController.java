package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.models.WishList;
import com.alten.shop.domain.services.WishListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wish-lists")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class WishListController {

    private final WishListService wishListService;

    @GetMapping("/get-content")
    public ResponseEntity<WishList> getWishList(Authentication authentication) {
        return ResponseEntity.ok(wishListService.getWishList(authentication));
    }

    @PostMapping("/add-product")
    public ResponseEntity<WishList> addProductToWishList(@RequestBody Product product, Authentication authentication) {
        return ResponseEntity.ok(wishListService.addProductToWishList(product, authentication));
    }

    @PutMapping("/remove-product")
    public ResponseEntity<WishList> removeProductFromWishList(@RequestBody Product product, Authentication authentication) {
        return ResponseEntity.ok(wishListService.removeProductionToWishList(product, authentication));
    }
}
