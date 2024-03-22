package com.aerospikeEcommerce.inventoryManagement.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Indexed;

@Builder
public class Product {
    @Id
   private String productId;


    private String productName;
    private String productDescription;
    private String productCategory;
    private Long productPrice;
    private Long productQuantity;

    public Product() {

    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Product(String productId, String productName, String productDescription, String productCategory,
                   Long productQuantity,Long productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity=productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }


}
