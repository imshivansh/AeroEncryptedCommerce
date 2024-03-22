package com.aerospikeEcommerce.inventoryManagement.service;


import com.aerospikeEcommerce.inventoryManagement.dto.OrderUpdateRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductResponse;
import com.aerospikeEcommerce.inventoryManagement.entity.Product;

import java.util.List;

public interface IInventoryService {
    ProductResponse addProduct(ProductRequest productRequest);

    List<Product> getAllProducts();

    Product getProductById(String id);

    Product updateInventoryOfAProduct(String id, ProductRequest productRequest);

    String updateOrderQuantity(OrderUpdateRequest orderUpdateRequest);
}
