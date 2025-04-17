package com.alten.shop.domain.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditMetadata {
    private long id;
    private String username;
    private String firstName;
    private String email;
    private String password;
    private boolean active;
}
