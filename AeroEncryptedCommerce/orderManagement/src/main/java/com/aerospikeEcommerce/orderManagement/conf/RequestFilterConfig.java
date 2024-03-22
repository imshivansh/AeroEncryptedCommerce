package com.aerospikeEcommerce.orderManagement.conf;


import com.aerospike.encryptDecrypt.utils.AesGcmService;
import com.aerospike.encryptDecrypt.utils.AppConstants;
import com.aerospike.encryptDecrypt.utils.IEncryptDecrypt;
import com.aerospikeEcommerce.orderManagement.utils.CustomRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

import javax.crypto.SecretKey;

@Configuration
public class RequestFilterConfig extends OncePerRequestFilter {


private final IEncryptDecrypt encryptDecrypt;
    public RequestFilterConfig() {
        this.encryptDecrypt = new AesGcmService();

    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Wrap the request and response to read and modify content
        if(Objects.equals(request.getMethod(), "POST") || Objects.equals(request.getMethod(), "PUT")){
            String encryptedData = request.getHeader("data");



            SecretKey secretKey = AppConstants.secretKey;

            String decryptedJson = encryptDecrypt.decrypt(secretKey,encryptedData);
            CustomRequestWrapper customRequestWrapper = new CustomRequestWrapper(request,decryptedJson);
//            customRequestWrapper.getInputStream();
            filterChain.doFilter(customRequestWrapper,response);
        }else {
            filterChain.doFilter(request,response);
        }

//        Class<?>targetClass = determineTargetClass(request);
//        Object requiredPojo =  deserialize(decryptedJson,targetClass);
//        request.setAttribute("requestObject", requiredPojo);


//        ByteArrayInputStream decryptedInputStream = new ByteArrayInputStream(decryptedJson.getBytes());
//        request = new HttpServletRequestWrapper(request) {
//            @Override
//            public ServletInputStream getInputStream() throws IOException {
//                return new ServletInputStreamWrapper(decryptedInputStream);
//            }
//        };

//        String encryptedData =  request.getHeader("data");
//        System.out.println(encryptedData +"  encrypted data");


//        wrappedRequest.replaceInputStream(decryptedData.getBytes());

//        request.setAttribute("orderRequest",decryptedJson);


    }

    private String getContentAsString(HttpServletRequest request) throws IOException {
        // Retrieve content from the request's input stream as a String
        // You can use a method like the one shown earlier to read the input stream
        // and convert it to a string
        // For example:
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
           stringBuilder.append(line);
         }
        return stringBuilder.toString();

    }
//    private Class<?> determineTargetClass(HttpServletRequest request) {
//        // Determine the target class based on the specific controller method being invoked
//        // You may use request information, URL patterns, or annotations to make this determination
//
//        // Retrieve the controller method
//        Method controllerMethod = (Method) request.getAttribute("org.springframework.web.method.HandlerMethod.method");
//
//        if (controllerMethod != null) {
//            DynamicRequestClass dynamicRequestClass = controllerMethod.getAnnotation(DynamicRequestClass.class);
//            if (dynamicRequestClass != null) {
//                return dynamicRequestClass.value();
//            }
//        }
//
//        // Handle the case when there's no annotation on the method
//        return DefaultRequest.class; // Replace with your default class
//    }
//
//    private Object deserialize(String data, Class<?> targetClass) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(data, targetClass);
//        } catch (Exception e) {
//            // Handle exceptions as needed
//            return null;
//        }
//    }
}
