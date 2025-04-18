package com.alten.shop.domain.services;

import com.alten.shop.domain.models.Product;
import com.alten.shop.runtime.rest.dtos.ProductCreateRequestDTO;
import com.alten.shop.runtime.rest.dtos.ProductUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Product create(ProductCreateRequestDTO requestBody);
    public Product findById(long id);
    public void delete(long id);
    public Product update(ProductUpdateRequestDTO requestBody, long id);
    public Page<Product> findAll(Pageable pageable);

}
