package com.aerospikeEcommerce.orderManagement.service;

import com.aerospikeEcommerce.orderManagement.dto.OrderRequest;
import com.aerospikeEcommerce.orderManagement.dto.OrderResponse;
import com.aerospikeEcommerce.orderManagement.entity.Order;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IOrderService {
    OrderResponse createNewOrder(OrderRequest orderRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException;
    List<Order> getAllOrders();

    Order getOrderById(String orderId);

    String deleteById(String id);
}
