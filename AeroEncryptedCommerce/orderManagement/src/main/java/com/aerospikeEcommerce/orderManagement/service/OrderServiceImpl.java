package com.aerospikeEcommerce.orderManagement.service;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.encryptDecrypt.utils.AesGcmService;
import com.aerospike.encryptDecrypt.utils.AppConstants;
import com.aerospike.encryptDecrypt.utils.IEncryptDecrypt;
import com.aerospikeEcommerce.orderManagement.dto.OrderRequest;
import com.aerospikeEcommerce.orderManagement.dto.OrderResponse;
import com.aerospikeEcommerce.orderManagement.dto.OrderUpdate;
import com.aerospikeEcommerce.orderManagement.dto.ProductDetails;
import com.aerospikeEcommerce.orderManagement.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class
OrderServiceImpl implements IOrderService{
    private final AerospikeClient
            aerospikeClient;
    private final String nameSpace;
     private final String set="ecommerce-order";
     @Autowired
     WebClient client;

     private final IEncryptDecrypt encryptDecrypt;

    public OrderServiceImpl(
                            @Value("${spring.aerospike.namespace}") String nameSpace,
                             AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
        this.nameSpace = nameSpace;
        encryptDecrypt = new AesGcmService();

    }

    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeyException {
         /*
    According to our project flow I am assuming here, by reaching to this method the product existence have been already
    verified so we no longer need to do it.
     */
     List<ProductDetails> productDetails = orderRequest.getProductDetails();
     Order order = new Order();
     order.setOrderId(UUID.randomUUID().toString());
     order.setUserId(orderRequest.getUserId());
     for (ProductDetails prodDetail:productDetails){
         order.setProductId(prodDetail.getId());
     }
        Bin orderIdBin = new Bin("orderId", order.getOrderId());
        Bin userIdBin = new Bin("userId", order.getUserId());
        Bin productIdsBin= new Bin("productIdBin",order.getProductIds());
        Key key = new Key(nameSpace,set, order.getOrderId());
        WritePolicy writePolicy = new WritePolicy();
        writePolicy.recordExistsAction= RecordExistsAction.CREATE_ONLY;
        aerospikeClient.put(writePolicy,key,orderIdBin,userIdBin,productIdsBin);
        OrderUpdate orderUpdate = new OrderUpdate(productDetails);
        //library util call
        String encryptedOrder = encryptDecrypt.encrypt(orderUpdate, AppConstants.secretKey);

        //microservice call to update the product inventory
        Mono<String>orderUpdateStatus = client.put()
                .uri("http://localhost:8082/inventory/products/updateOrderQuantity")
                .contentType(MediaType.APPLICATION_JSON)
                .header("data",encryptedOrder)
                .retrieve()
                .bodyToMono(String.class)
                ;
        orderUpdateStatus.block();
        System.out.println(aerospikeClient.get(null,key));
        return null;
    }

    public Order getOrderById(String orderId){
        Order order;
        Key key = new Key(nameSpace,set,orderId);
        Record record = aerospikeClient.get(null,key);
        if(record!=null) {
            Map<String, Object> bins = record.bins;
            order = new Order();

            order.setOrderId((String) bins.get("orderId"));
            order.setProductIds((List<String>) bins.get("productIdBin"));
            order.setUserId((String)bins.get("userId"));
        }else{
            throw new RuntimeException("recordDoes not exist");
        }
        return order;


    }

    public List<Order>getAllOrders(){
        List<Order>orderList= new ArrayList<>();
        Statement statement= new Statement();
        statement.setNamespace(nameSpace);
        statement.setSetName(set);
        RecordSet recordSet = aerospikeClient.query(null,statement);

        while (recordSet.next()){
            Order order = new Order();
            order.setOrderId(recordSet.getRecord().getString("orderId"));
            order.setUserId(recordSet.getRecord().getString("userId"));
            order.setProductIds((List<String>) recordSet.getRecord().getList("productIdBin"));

            orderList.add(order);

        }
        return  orderList;

    }

    public String deleteById(String orderId){
        Key key = new Key(nameSpace,set,orderId);
        boolean delete = aerospikeClient.delete(null, key);
        if(delete){
            return String.format("Order with id: %s, deleted successfully",orderId);
        }
        return "Order with given Id not found";
    }

}

