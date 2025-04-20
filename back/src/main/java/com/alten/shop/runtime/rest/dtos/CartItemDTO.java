package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.NotNull;

public record CartItemDTO (
        @NotNull int quantity,
        @NotNull ProductItemDTO product
){ }
