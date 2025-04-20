package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findFirstByUserId(long id);
}
