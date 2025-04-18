package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.models.User;
import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.rest.dtos.AuthenticationRequestDTO;
import com.alten.shop.runtime.rest.dtos.AuthenticationResponseDTO;
import com.alten.shop.runtime.rest.dtos.UserRequestDTO;
import com.alten.shop.runtime.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @PostMapping( "/token")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO body) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.email(), body.password()));
        if (authentication.isAuthenticated()) {
            log.info("Authentication successful");
            return ResponseEntity.ok(
                new AuthenticationResponseDTO(
                        jwtUtils.generateToken((UserDetails) authentication.getPrincipal()),
                        jwtUtils.tokenDuration()
                )

            );
        } else throw new BadCredentialsException("Invalid Credentials");
    }

    @PostMapping("/account")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequestDTO body) {
        return new ResponseEntity<>(userService.register(body), HttpStatus.CREATED);
    }
}
