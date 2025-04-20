package com.alten.shop.domain.services;


import com.alten.shop.domain.models.WishList;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import org.springframework.security.core.Authentication;

public interface WishListService {
    public WishList  addProductToWishList(ProductItemDTO productItem, Authentication authentication);
    public WishList removeProductionToWishList(ProductItemDTO product, Authentication authentication);
    public WishList getWishList(Authentication authentication);
}
