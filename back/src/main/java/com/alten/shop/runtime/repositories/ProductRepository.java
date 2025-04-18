package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstByCode(String code);
    Optional<Product> findFirstByInternalReference(String internalReference);
    Optional<Product> findFirstByInternalReferenceAndIdNot(String internalReference, long id);
}
