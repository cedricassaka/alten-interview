package com.alten.shop.runtime.rest.dtos;

public record AuthenticationResponseDTO (
        String token,
        long expireIn
) { }
