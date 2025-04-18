package com.alten.shop.runtime.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequestDTO (
    @NotEmpty String username,
    @NotEmpty @JsonProperty("first_name") String firstName,
    @NotEmpty @Email String email,
    @NotEmpty @Size(min = 2, message = "{validation.name.size.too_short}") String password
) {}
