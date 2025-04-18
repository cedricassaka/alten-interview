package com.alten.shop.runtime.repositories;

import com.alten.shop.domain.models.Product;
import com.alten.shop.domain.models.User;
import com.alten.shop.runtime.config.TestAuditingConfiguration;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("abc@abc.com")
                .username("abc")
                .firstName("abc").
                password("abc")
                .build();
    }

    @Test
    void givenUserCreated_whenFindByEmail_thenFailure() {
        userRepository.save(user);
        assertFalse(userRepository.findFirstByEmail("acb").isPresent());
    }

    @Test
    void givenUserCreated_whenFindByEmail_thenSuccess() {
        userRepository.save(user);
        assertTrue(userRepository.findFirstByEmail(user.getEmail()).isPresent());
    }

    @Test
    void givenUserCreated_whenFindByUsername_thenFailure() {
        userRepository.save(user);
        assertFalse(userRepository.findFirstByUsername("acb").isPresent());
    }

    @Test
    void givenUserCreated_whenFindByUsername_thenSuccess() {
        userRepository.save(user);
        assertTrue(userRepository.findFirstByUsername(user.getUsername()).isPresent());
    }

}