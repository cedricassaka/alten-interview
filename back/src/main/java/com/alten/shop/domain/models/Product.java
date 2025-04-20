package com.alten.shop.domain.models;

import com.alten.shop.domain.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@ToString
public class Product extends AuditMetadata {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
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
