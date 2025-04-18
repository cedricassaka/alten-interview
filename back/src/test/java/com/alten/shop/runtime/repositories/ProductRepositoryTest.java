package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.enums.InventoryStatus;
import com.alten.shop.domain.models.Product;
import com.alten.shop.runtime.config.TestAuditingConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestAuditingConfiguration.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    Product firstProduct;
    Product secondProduct;

    @BeforeEach
    void setUp() {
        firstProduct = Product.builder()
                .code("xyz")
                .category("category")
                .description("")
                .name("xyz")
                .inventoryStatus(InventoryStatus.INSTOCK)
                .quantity(15)
                .internalReference("IRF")
                .price(12.5)
                .rating(0.5)
                .build();

        secondProduct = Product.builder()
                .code("xyz 2")
                .category("category")
                .description("")
                .name("xyz 2")
                .inventoryStatus(InventoryStatus.INSTOCK)
                .quantity(11)
                .internalReference("IRF 2")
                .price(65.2)
                .rating(4)
                .build();
    }

    @Test
    void givenProductCreated_whenFindByCode_thenFailure() {
        repository.save(firstProduct);
        assertFalse(repository.findFirstByCode("xzy").isPresent());
    }

    @Test
    void givenProductCreated_whenFindByCode_thenSuccess() {
        repository.save(firstProduct);
        assertTrue(repository.findFirstByCode(firstProduct.getCode()).isPresent());
    }

    @Test
    void givenProductCreated_whenFindByInternalReference_thenFailure() {
        repository.save(secondProduct);
        assertFalse(repository.findFirstByInternalReference("IRF 3").isPresent());
    }

    @Test
    void givenProductCreated_whenFindByinternalReference_thenSuccess() {
        repository.save(secondProduct);
        assertTrue(repository.findFirstByInternalReference(secondProduct.getInternalReference()).isPresent());
    }

    @Test
    void givenProductCreated_whenFindByInternalReferenceAlreadyExist_thenFailure() {
        repository.save(firstProduct);
        repository.save(secondProduct);
        assertFalse(repository.findFirstByInternalReferenceAndIdNot(secondProduct.getInternalReference(), secondProduct.getId()).isPresent());
    }

    @Test
    void givenProductCreated_whenFindByInternalReferenceAlreadyExist_thenSuccess() {
        repository.save(firstProduct);
        repository.save(secondProduct);
        assertTrue(repository.findFirstByInternalReferenceAndIdNot(firstProduct.getInternalReference(), secondProduct.getId()).isPresent());
    }


}