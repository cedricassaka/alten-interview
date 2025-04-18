package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}
