package com.aerospikeEcommerce.inventoryManagement.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderUpdateRequest {
    private List<ProductDetails> productDetailsList=new ArrayList<>();
    public  OrderUpdateRequest(){

    }


    public OrderUpdateRequest(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }

    public void setProductDetailsList(List<ProductDetails> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }
}
