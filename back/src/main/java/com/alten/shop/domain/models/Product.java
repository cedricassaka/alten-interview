package com.alten.shop.domain.models;

import com.alten.shop.domain.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends AuditMetadata {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    @Column(nullable = false, unique = true)
    private String internalReference;
    private long shellId;
    private double rating;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
}
