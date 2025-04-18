package com.alten.shop.runtime.rest.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private String token;
    private long expireIn;
}
