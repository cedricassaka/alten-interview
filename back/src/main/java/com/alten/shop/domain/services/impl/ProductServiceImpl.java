package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.enums.InventoryStatus;
import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.services.ProductService;
import com.alten.shop.runtime.handler.exception.ResourceNotFoundException;
import com.alten.shop.runtime.repositories.ProductRepository;
import com.alten.shop.runtime.rest.dtos.ProductCreateRequestDTO;
import com.alten.shop.runtime.rest.dtos.ProductUpdateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ObjectMapper mapper;

    private final ProductRepository repository;

    @Override
    @Transactional
    public Product create(ProductCreateRequestDTO requestBody) {
        if (repository.findFirstByInternalReference(requestBody.getInternalReference()).isPresent())
            throw new DuplicateKeyException("internalReference");
        Product product = mapper.convertValue(requestBody, Product.class);
        product.setCode(generateUniqueCode());
        product.setInventoryStatus(product.getQuantity() == 0 ? InventoryStatus.OUTOFSTOCK :
                product.getQuantity() < 10  ? InventoryStatus.LOWSTOCK : InventoryStatus.INSTOCK);
        return repository.save(product);
    }

    @Override
    public Product findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + id));
    }

    @Override
    public void delete(long id) {
        repository.delete(findById(id));
    }

    @Override
    @Transactional
    public Product update(ProductUpdateRequestDTO requestBody, long id) {
        Product product = findById(id);
        if (repository.findFirstByInternalReferenceAndIdNot(requestBody.getInternalReference(), id).isPresent())
            throw new DuplicateKeyException("internalReference");
        String[] nulls = getNullPropertiesNames(requestBody);
        BeanUtils.copyProperties(requestBody, product, nulls);
        product.setInventoryStatus(product.getQuantity() == 0 ? InventoryStatus.OUTOFSTOCK :
                product.getQuantity() < 10  ? InventoryStatus.LOWSTOCK : InventoryStatus.INSTOCK);

        return repository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID().toString();
        } while (repository.findFirstByCode(code).isPresent());
        return code;
    }

    private String[] getNullPropertiesNames(ProductUpdateRequestDTO source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
