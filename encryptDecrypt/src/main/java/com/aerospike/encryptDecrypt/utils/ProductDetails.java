package com.aerospike.encryptDecrypt.utils;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ProductDetails implements Serializable {
    private String id;
    private Long quantity;




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
