package com.alten.shop.runtime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@Profile("test")
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider")
public class TestAuditingConfiguration {

}
