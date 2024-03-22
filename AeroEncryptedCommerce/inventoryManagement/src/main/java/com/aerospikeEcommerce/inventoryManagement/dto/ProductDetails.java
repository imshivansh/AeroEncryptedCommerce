package com.aerospikeEcommerce.inventoryManagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductDetails {
    private String id;
    private Long quantity;

    public ProductDetails(String id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
