package com.alten.shop.domain.services;

import com.alten.shop.domain.models.Cart;
import com.alten.shop.domain.models.CartItem;
import com.alten.shop.domain.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CartService {
    public Cart addProductToCart(CartItem cartItem, Authentication authentication);
    public Cart removeProductToCart(Product product, Authentication authentication);
    public Page<CartItem> getCartContent(Authentication authentication, Pageable pageable);
}
