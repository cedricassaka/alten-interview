package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.services.ProductService;
import com.alten.shop.runtime.rest.dtos.ProductCreateRequestDTO;
import com.alten.shop.runtime.rest.dtos.ProductUpdateRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(Pageable pageable) {
        log.info("get all products");
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        log.info("get a product with id {}", id);
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("delete a product with id {}", id);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateRequestDTO body) {
        log.info("create a new product");
        return new ResponseEntity<>(productService.create(body), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductUpdateRequestDTO body, @PathVariable long id) {
        log.info("update the product with id {}", id);
        return ResponseEntity.ok(productService.update(body, id));
    }

}
