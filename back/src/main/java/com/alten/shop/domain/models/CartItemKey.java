package com.alten.shop.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemKey implements Serializable {
    @Column(name = "cart_id")
    Long cartId;

    @Column(name = "product_id")
    Long productId;

}
