package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequestDTO (@Email @NotNull String email, @NotNull String password) {}