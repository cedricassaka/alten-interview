package com.alten.shop.runtime.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDTO {
    @NotEmpty
    private String name;
    private String description;
    private String image;
    @NotEmpty
    private String category;
    @Min(1)
    private double price;
    @Min(0)
    private int quantity;
    @NotEmpty
    @Size(min = 2)
    private String internalReference;
    private long shellId;
    @Min(0)
    private double rating;
}
