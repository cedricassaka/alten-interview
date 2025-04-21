package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.CartItem;
import com.alten.shop.domain.models.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {
}
