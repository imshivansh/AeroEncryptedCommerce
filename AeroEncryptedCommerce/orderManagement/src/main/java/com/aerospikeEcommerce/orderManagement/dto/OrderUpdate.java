package com.aerospikeEcommerce.orderManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class OrderUpdate {
    private List<ProductDetails> productDetailsList;

    public OrderUpdate(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }

    public List<ProductDetails> getProductDetailsList() {
        return productDetailsList;
    }

    public void setProductDetailsList(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }
}
