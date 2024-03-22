package com.aerospikeEcommerce.inventoryManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String description;
    private String category;



    private Long price;
    private Long quantity;


    public ProductRequest(String productName, String description, String category, Long price, Long quantity) {
        this.productName = productName;
       this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
