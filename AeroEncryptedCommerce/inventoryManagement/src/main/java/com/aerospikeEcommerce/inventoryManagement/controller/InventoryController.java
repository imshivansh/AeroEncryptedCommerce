package com.aerospikeEcommerce.inventoryManagement.controller;

import com.aerospikeEcommerce.inventoryManagement.dto.OrderUpdateRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductResponse;
import com.aerospikeEcommerce.inventoryManagement.entity.Product;
import com.aerospikeEcommerce.inventoryManagement.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@RestController
@RequestMapping("/inventory/products/")

public class InventoryController {
    @Autowired
    IInventoryService inventoryService;

    @PostMapping("addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProductToInventory(@RequestBody ProductRequest productRequest){
        return inventoryService.addProduct(productRequest);

    }


    @GetMapping("findById/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product getProductById(@PathVariable String id){
        return  inventoryService.getProductById(id);
    }
    @PutMapping("updateInventoryProduct/{id}")
    public Product updateInventoryProduct(@PathVariable String id,@RequestBody ProductRequest productRequest){
        return inventoryService.updateInventoryOfAProduct(id,productRequest);
    }



    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("updateOrderQuantity")
    public  String updateInventoryForOrderBeingPlaced(@RequestBody OrderUpdateRequest orderUpdateRequest){
        return inventoryService.updateOrderQuantity(orderUpdateRequest);
    }


    @GetMapping("allProducts")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Product>getAllProductsFromInventory(){
        return inventoryService.getAllProducts();
    }


}
