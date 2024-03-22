package com.aerospikeEcommerce.orderManagement.controller;


import com.aerospikeEcommerce.orderManagement.dto.OrderRequest;
import com.aerospikeEcommerce.orderManagement.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("createOrder")
    public ResponseEntity<?>generateNewOrderRequest(@RequestBody OrderRequest orderRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
        return  ResponseEntity.ok().body(orderService.createNewOrder(orderRequest));
    }

//    @PostMapping("encrypt")
//    public String encrypted(@RequestBody OrderRequest orderRequest){
//        AesGcmService aesGcmService = new AesGcmService();
//        return  aesGcmService.encrypt(orderRequest, Constants.secretKey);
//    }

    @GetMapping("getOrderById/{id}")
    public ResponseEntity<?>fetchOrderById(@PathVariable("id") String orderId){
        return  ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?>deleteById(@PathVariable String id){
        return ResponseEntity.ok().body(orderService.deleteById(id));
    }
    @GetMapping("allOrders")

    public ResponseEntity<?>getAllOrders(){
        return  ResponseEntity.ok().body(orderService.getAllOrders());
    }
}
