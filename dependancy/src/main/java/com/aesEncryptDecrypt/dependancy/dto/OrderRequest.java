package com.aesEncryptDecrypt.dependancy.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest implements Serializable {
    private String userId;
    private List<ProductDetails> productDetails;
    private transient ByteArrayOutputStream byteArrayOutputStream;
}
