package com.aerospikeEcommerce.orderManagement.dto;

import lombok.NoArgsConstructor;


public class Product {
    private final String productName;
    private final String Description;
    private final String productCategory;


    public Product(String productName, String description, String productCategory, Double price) {
        this.productName = productName;
        Description = description;
        this.productCategory = productCategory;
        this.price = price;
    }

    private final Double price;

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return Description;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public Double getPrice() {
        return price;
    }
}
