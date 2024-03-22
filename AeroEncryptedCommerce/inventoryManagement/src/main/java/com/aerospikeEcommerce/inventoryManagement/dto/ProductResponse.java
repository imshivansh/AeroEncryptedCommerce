package com.aerospikeEcommerce.inventoryManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {
    @Getter
    private String productId;

    public ProductResponse(String productId, String name, String description, String category, Long price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    private String name;
    private  String description;
    private  String category;


    public ProductResponse(String name, String description, String category, Long price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    private  Long price;

    @Override
    public String toString() {
        return "ProductResponse{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
