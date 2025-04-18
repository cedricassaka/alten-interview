package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Min(4)
    private String password;
}
