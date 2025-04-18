package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record ProductCreateRequestDTO (
    @NotEmpty String name,
    String description,
    String image,
    @NotEmpty String category,
    @Min(1) double price,
    @Min(0) int quantity,
    @NotEmpty @Size(min = 2) String internalReference,
    long shellId,
    @Min(0) double rating
) {}
