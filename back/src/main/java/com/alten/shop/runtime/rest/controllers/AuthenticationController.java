package com.alten.shop.runtime.rest.controllers;

import com.alten.shop.domain.services.UserService;
import com.alten.shop.runtime.rest.dtos.AuthenticationRequestDTO;
import com.alten.shop.runtime.rest.dtos.AuthenticationResponseDTO;
import com.alten.shop.runtime.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO body) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(
                    AuthenticationResponseDTO.builder()
                            .token(jwtUtils.generateToken((UserDetails) authentication.getPrincipal()))
                            .expireIn(jwtUtils.tokenDuration())
                            .build()
            );
        } else throw new BadCredentialsException("Invalid Credential");
    }
}
