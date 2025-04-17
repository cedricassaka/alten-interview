package com.alten.shop.domain.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

public abstract class AuditMetadata {
    @CreatedDate
    protected Instant createdAt;
    @LastModifiedDate
    protected Instant updatedAt;
}
