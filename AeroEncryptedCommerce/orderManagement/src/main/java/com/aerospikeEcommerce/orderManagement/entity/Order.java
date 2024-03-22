package com.aerospikeEcommerce.orderManagement.entity;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;



public class Order {
    @Id
    private String orderId;
    private String userId;
    private final List<String> productIds;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Order() {
        productIds= new ArrayList<>();

    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIdss) {
        productIds.addAll(productIdss);
    }

    public void setProductId(String productId) {
        this.productIds.add(productId);
    }

    public Order(String orderId,String userId, List<String> productIds) {
        this.orderId = orderId;
        this.userId = userId;
        this.productIds = productIds;
    }
}
