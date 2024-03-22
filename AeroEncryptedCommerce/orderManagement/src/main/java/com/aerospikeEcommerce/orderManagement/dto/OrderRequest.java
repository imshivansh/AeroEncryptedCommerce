package com.aerospikeEcommerce.orderManagement.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderRequest implements Serializable {

    public OrderRequest(){

    }
    private String userId;
    private List<ProductDetails> productDetails;

    private transient ByteArrayOutputStream byteArrayOutputStream;

     @JsonIgnore
    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }
     @JsonIgnore
    public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public OrderRequest(String userId, List<ProductDetails> productDetails) {
        this.userId = userId;
        this.productDetails = productDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }
}
