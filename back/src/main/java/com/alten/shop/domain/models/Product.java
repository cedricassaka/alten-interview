package com.alten.shop.domain.models;

import com.alten.shop.domain.enums.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends AuditMetadata {
    private long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private long shellId;
    private double rating;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
}
