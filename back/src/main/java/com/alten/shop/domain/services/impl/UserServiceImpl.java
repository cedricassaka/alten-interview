package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.models.User;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.repositories.UserRepository;
import com.alten.shop.runtime.rest.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Optional<User> authenticate(String email) {
        return repository.findFirstByEmail(email);
    }

    @Override
    public User register(UserRequestDTO userRequestDTO) {
        return null;
    }
}
