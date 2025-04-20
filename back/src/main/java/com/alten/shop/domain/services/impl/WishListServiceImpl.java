package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.models.User;
import com.alten.shop.domain.models.WishList;
import com.alten.shop.domain.services.ProductService;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.domain.services.WishListService;
import com.alten.shop.runtime.handler.exception.ResourceNotFoundException;
import com.alten.shop.runtime.repositories.WishListRepository;
import com.alten.shop.runtime.rest.dtos.ProductItemDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository repository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    @Transactional
    public WishList addProductToWishList(ProductItemDTO productItem, Authentication authentication) {
        WishList wishList = getWishList(authentication);
        boolean productExist = wishList.getProducts().stream()
                .anyMatch(productInList -> Objects.equals(productInList.getId(), productItem.id()));
        if (productExist) throw new DuplicateKeyException("Product in the wish list");
        Product product = productService.findById(productItem.id());
        wishList.getProducts().add(product);
        return repository.save(wishList);
    }

    @Override
    @Transactional
    public WishList removeProductionToWishList(ProductItemDTO product, Authentication authentication) {
        WishList wishList = getWishList(authentication);
        Optional<Product> productExist = wishList.getProducts().stream()
                .filter(productInList -> Objects.equals(productInList.getId(), product.id())).findFirst();
        if (wishList.getProducts().isEmpty() || productExist.isEmpty()) throw new ResourceNotFoundException("Product not found in wish list");
        Product existingProduct = productExist.get();
        wishList.getProducts().remove(existingProduct);
        return wishList;
    }

    @Override
    public WishList getWishList(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        return repository.findFirstByUserId(user.getId())
                .orElse(
                    WishList.builder()
                            .user(user)
                            .products(new HashSet<>())
                            .build()
                );
    }
}
