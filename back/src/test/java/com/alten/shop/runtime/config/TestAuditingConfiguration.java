package com.alten.shop.runtime.config;

import com.alten.shop.domain.services.impl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@Profile("test")
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider")
public class TestAuditingConfiguration {
    @Bean
    @Primary
    AuditorAwareImpl auditorProvider() {
        return new TestAuditorAware();
    }

    public static class TestAuditorAware extends AuditorAwareImpl {

        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of("test");
        }
    }
}
