package com.alten.shop.runtime.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @JsonProperty("first_name")
    private String firstName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 2, message = "{validation.name.size.too_short}")
    private String password;
}
