package com.alten.shop.domain.services;


import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.models.WishList;
import org.springframework.security.core.Authentication;

public interface WishListService {
    public WishList  addProductToWishList(Product product, Authentication authentication);
    public WishList removeProductionToWishList(Product product, Authentication authentication);
    public WishList getWishList(Authentication authentication);
}
