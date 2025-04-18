package com.alten.shop.domain.services;

import com.alten.shop.domain.models.User;
import com.alten.shop.runtime.rest.dtos.UserRequestDTO;

import java.util.Optional;

public interface UserService {

    public Optional<User> authenticate(String email);
    public User register(UserRequestDTO userRequestDTO);
}
