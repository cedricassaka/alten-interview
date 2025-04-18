package com.alten.shop.domain.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.authenticate(username)
                .map(user -> {
                    Set<GrantedAuthority> authorities = new HashSet<>();
                    if (username.equalsIgnoreCase("admin@admin.com")) authorities.add(new SimpleGrantedAuthority("admin"));
                    return new User(user.getEmail(), user.getPassword(), authorities);
                })
                .orElseThrow(() -> new BadCredentialsException("Bad Credentials Exception"));
    }
}
