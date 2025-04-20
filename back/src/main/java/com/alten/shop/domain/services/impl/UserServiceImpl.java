package com.alten.shop.domain.services.impl;

import com.alten.shop.domain.models.User;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.handler.exception.ResourceNotFoundException;
import com.alten.shop.runtime.repositories.UserRepository;
import com.alten.shop.runtime.rest.dtos.UserRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByEmail(String email) {
        return repository.findFirstByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
    }

    @Override
    @Transactional
    public User register(UserRequestDTO userRequestDTO) {
        if (repository.findFirstByUsername(userRequestDTO.username()).isPresent())
            throw new DuplicateKeyException("username");
        if (repository.findFirstByEmail(userRequestDTO.email()).isPresent())
            throw new DuplicateKeyException("email");

        User user = mapper.convertValue(userRequestDTO, User.class);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        return repository.save(user);
    }
}
