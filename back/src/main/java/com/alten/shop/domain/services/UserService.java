package com.alten.shop.domain.services;

import com.alten.shop.domain.models.User;
import com.alten.shop.runtime.rest.dtos.UserRequestDTO;

public interface UserService {
    public User findByEmail(String email);
    public User register(UserRequestDTO userRequestDTO);
}
