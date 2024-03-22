package com.aerospikeEcommerce.orderManagement.dto;

import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private String orderId;
    private List<Product> products;
    private Double totalOrderAmount;
}
