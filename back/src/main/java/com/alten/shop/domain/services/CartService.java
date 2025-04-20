package com.alten.shop.domain.services;

import com.alten.shop.domain.models.CartItem;
import com.alten.shop.runtime.rest.dtos.CartItemDTO;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CartService {
    public Page<CartItem> addProductToCart(CartItemDTO cartItemDTO, Authentication authentication, Pageable pageable);
    public Page<CartItem> removeProductToCart(ProductItemDTO product, Authentication authentication, Pageable pageable);
    public Page<CartItem> getCartContent(Authentication authentication, Pageable pageable);
}
