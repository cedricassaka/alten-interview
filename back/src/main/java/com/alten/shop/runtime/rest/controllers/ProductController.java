package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.services.ProductService;
import com.alten.shop.runtime.rest.dtos.ProductCreateRequestDTO;
import com.alten.shop.runtime.rest.dtos.ProductUpdateRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PagedModel<Product>> getProducts(Pageable pageable, PagedResourcesAssembler assembler) {
        log.info("get all products");
        return ResponseEntity.ok(assembler.toModel(productService.findAll(pageable)));
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
