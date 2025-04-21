package com.alten.shop.domain.services;

import com.alten.shop.domain.models.Cart;
import com.alten.shop.runtime.rest.dtos.CartItemDTO;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import org.springframework.security.core.Authentication;

public interface CartService {
    public Cart addProductToCart(CartItemDTO cartItemDTO, Authentication authentication);
    public Cart removeProductToCart(ProductItemDTO product, Authentication authentication);
    public Cart getCart(Authentication authentication);
}
