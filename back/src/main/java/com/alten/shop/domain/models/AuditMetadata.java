package com.alten.shop.domain.models;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class AuditMetadata {
    @CreatedDate
    protected Instant createdAt;
    @LastModifiedDate
    protected Instant updatedAt;
}
