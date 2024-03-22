package com.aerospikeEcommerce.orderManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ProductDetails implements Serializable {

    public ProductDetails(){

    }
    private String id;
    private Long quantity;
    @JsonIgnore(value = true)
    private transient ByteArrayOutputStream byteArrayOutputStream;


    public ProductDetails(String id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
