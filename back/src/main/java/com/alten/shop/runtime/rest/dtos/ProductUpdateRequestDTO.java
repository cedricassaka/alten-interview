package com.alten.shop.runtime.rest.dtos;


public record ProductUpdateRequestDTO (
     String name,
     String description,
     String image,
     String category,
     double price,
     int quantity,
     String internalReference,
     long shellId,
     double rating
) {}
