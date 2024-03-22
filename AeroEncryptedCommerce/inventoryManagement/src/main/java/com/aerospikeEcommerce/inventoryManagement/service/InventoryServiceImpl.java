package com.aerospikeEcommerce.inventoryManagement.service;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospikeEcommerce.inventoryManagement.dto.OrderUpdateRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductDetails;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductRequest;
import com.aerospikeEcommerce.inventoryManagement.dto.ProductResponse;
import com.aerospikeEcommerce.inventoryManagement.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements  IInventoryService{
    private final AerospikeClient aerospikeClient;

    private final String nameSpace;
    private final String set= "inventory";

    public InventoryServiceImpl(AerospikeClient aerospikeClient, @Value("${spring.aerospike.namespace}") String nameSpace) {
        this.aerospikeClient = aerospikeClient;
        this.nameSpace = nameSpace;
    }



    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Statement statement = new Statement();
        statement.setNamespace(nameSpace);
        statement.setSetName(set);

        ProductResponse productResponse = null;
        String indexName = "productName_index";
        String binName = "productName";
        aerospikeClient.createIndex(null, nameSpace, set, indexName, binName, com.aerospike.client.query.IndexType.STRING);
        statement.setFilter(Filter.equal("productName", productRequest.getProductName()));
        RecordSet records=aerospikeClient.query(null,statement);
            if(!records.next()){
                Product product = new Product(UUID.randomUUID().toString(), productRequest.getProductName(),
                        productRequest.getDescription(),productRequest.getCategory(),
                        productRequest.getQuantity(),productRequest.getPrice());
                Key key = new Key(nameSpace,set,product.getProductId());
                Bin productId=new Bin("productId",product.getProductId());
                Bin productDescription = new Bin("productDes",product.getProductDescription());
                Bin productName = new Bin("productName",product.getProductName());
                Bin productCategory = new Bin("productCat",product.getProductCategory());
                Bin productPrice = new Bin("productPrice",product.getProductPrice());
                Bin productQuantity = new Bin("productQuan",product.getProductQuantity());
                aerospikeClient.put(null,key,productId,productDescription,productName,
                        productCategory,productPrice,productQuantity);
                Record record = aerospikeClient.get(null, key);
                productResponse= new ProductResponse(record.getString("productName"),record.getString("productId"),record.getString("productDes"),
                        record.getString("productCat"),record.getLong("productPrice"));

            }else {
                Record record = records.getRecord();
                Bin productId=new Bin("productId",record.getString("productId"));
                Bin productDescription = new Bin("productDes",record.getString("productDes"));
                Bin productName = new Bin("productName",record.getString("productName"));
                Bin productCategory = new Bin("productCat",record.getString("productCat"));
                Bin productPrice = new Bin("productPrice",record.getLong("productPrice"));
                Bin productQuantity = new Bin("productQuan",record.getLong("productQuan")+productRequest.getQuantity());

                Key key = new Key(nameSpace,set,productId.value.toString());
                aerospikeClient.put(null,key,productId,productDescription,productName,productCategory,productPrice
                        ,productQuantity);
                Record recordNotPresentBefore = aerospikeClient.get(null, key);
                productResponse=  new ProductResponse(recordNotPresentBefore.getString("productId"),recordNotPresentBefore.getString("productName"),recordNotPresentBefore.getString("productDes"),
                        recordNotPresentBefore.getString("productCat"),recordNotPresentBefore.getLong("productPrice"));

            }

        return  productResponse;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product>allProducts= new ArrayList<>();
        Statement statement = new Statement();
        statement.setNamespace(nameSpace);
        statement.setSetName(set);
        RecordSet records = aerospikeClient.query(null,statement);
        while (records.next()){
            allProducts.add(new Product(
                    records.getRecord().getString("productId"),
                    records.getRecord().getString("productName"),
                    records.getRecord().getString("productDes"),
                    records.getRecord().getString("productCat"),
                    records.getRecord().getLong("productQuan"),
                    records.getRecord().getLong("productPrice")
            ));
        }
  return  allProducts;
    }

    @Override
    public Product getProductById(String id) {
        Key key = new Key(nameSpace,set,id);
        Record record = aerospikeClient.get(null, key);
        Product product;
        if(record!=null){
            product = new Product(
                    record.getString("productId"),
                    record.getString("productName"),
                    record.getString("productDes"),
                    record.getString("productCat"),
                    record.getLong("productQuan"),
                    record.getLong("productPrice")
            );
        }else {
            throw new RuntimeException("record not found");
        }
        return  product;
    }

    public Product updateInventoryOfAProduct( String id,ProductRequest productRequest){
        Key key = new Key(nameSpace,set,id);
        Record recordBeforeUpdate = aerospikeClient.get(null,key);
        //updating the quantity
        long quantity = recordBeforeUpdate.getLong("productQuan");
        quantity+=productRequest.getQuantity();

        Record updatedRecord = aerospikeClient.operate(null, key,
                Operation.put(new Bin("productId", com.aerospike.client.Value.get(id))),
                Operation.put(new Bin("productName", com.aerospike.client.Value.get(productRequest.getProductName()))),
                Operation.put(new Bin("productQuan", com.aerospike.client.Value.get(quantity))),
                        Operation.put(new Bin("productDes", com.aerospike.client.Value.get(productRequest.getDescription()))),
                                Operation.put(new Bin("productPrice", com.aerospike.client.Value.get(productRequest.getPrice()))),
                                        Operation.put(new Bin("productCat", com.aerospike.client.Value.get(productRequest.getCategory())))
        );
        Record record = aerospikeClient.get(null,key);
        return  new Product(record.getString("productId"),record.getString("productName"),
                record.getString("productDes"),record.getString("productCat"),
                record.getLong("productQuan"),record.getLong("productPrice"));

    }

    @Override
    public String updateOrderQuantity( OrderUpdateRequest orderQuantityUpdateRequest) {

        List<ProductDetails>productDetailsList = orderQuantityUpdateRequest.getProductDetailsList();
        for(ProductDetails prodDetails:productDetailsList){
            String id = prodDetails.getId();
            Key key = new Key(nameSpace,set,id);
            Record recordBeforeUpdate = aerospikeClient.get(null,key);
            //updating the quantity
            long quantity = recordBeforeUpdate.getLong("productQuan");
            quantity-=prodDetails.getQuantity();
            if(quantity<0){
                throw new RuntimeException("Do not have required number of product for the product id :"+id+" "+"kindly try reducing the number of products to be ordered, so that we can process it accurately and can serve you better ..");
            }
            aerospikeClient.operate(null,key,
                    Operation.put(new Bin("productQuan", com.aerospike.client.Value.get(quantity))));

            System.out.println("Inventory updated for product id: "+id+" ,"+" Total number of products available are: "+ quantity);
        }
        return "All the products have been updated successfully";

    }



}