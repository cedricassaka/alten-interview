package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.User;
import com.alten.shop.runtime.config.TestAuditingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(TestAuditingConfiguration.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUserCreated_whenFindByEmail_thenFailure() {
        User user = User.builder()
                .email("abc@abc.com")
                .username("abc")
                .firstName("abc").
                password("abc")
                .build();
        userRepository.save(user);
        assertFalse(userRepository.findFirstByEmail("acb").isPresent());
    }

    @Test
    void givenUserCreated_whenFindByEmail_thenSuccess() {
        User user = User.builder()
                .email("abc@abc.com")
                .username("abc")
                .firstName("abc").
                password("abc")
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findFirstByEmail(user.getEmail()).isPresent());
    }

    @Test
    void givenUserCreated_whenFindByUsername_thenFailure() {
        User user = User.builder()
                .email("abc@abc.com")
                .username("abc")
                .firstName("abc").
                password("abc")
                .build();
        userRepository.save(user);
        assertFalse(userRepository.findFirstByUsername("acb").isPresent());
    }

    @Test
    void givenUserCreated_whenFindByUsername_thenSuccess() {
        User user = User.builder()
                .email("abc@abc.com")
                .username("abc")
                .firstName("abc").
                password("abc")
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findFirstByUsername(user.getUsername()).isPresent());
    }

}