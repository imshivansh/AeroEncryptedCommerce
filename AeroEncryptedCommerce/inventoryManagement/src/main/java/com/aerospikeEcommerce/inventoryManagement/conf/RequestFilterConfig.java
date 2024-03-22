package com.aerospikeEcommerce.inventoryManagement.conf;

import com.aerospike.encryptDecrypt.utils.AesGcmService;
import com.aerospike.encryptDecrypt.utils.AppConstants;
import com.aerospike.encryptDecrypt.utils.IEncryptDecrypt;
import com.aerospikeEcommerce.inventoryManagement.utils.CustomRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Configuration
public class RequestFilterConfig extends OncePerRequestFilter {

    private final IEncryptDecrypt encryptDecrypt;
            public RequestFilterConfig(){
        encryptDecrypt =  new AesGcmService();
            }
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (Objects.equals(request.getMethod(), "PUT") || Objects.equals(request.getMethod(),
                "POST")) {
            String body = request.getQueryString();
            String json = getContentAsString(request);

            String encryptedJson = request.getHeader("data");
                String decryptedJson = encryptDecrypt.decrypt(AppConstants.secretKey, json);
                CustomRequestWrapper customRequestWrapper =  new CustomRequestWrapper(request,decryptedJson);
                filterChain.doFilter(customRequestWrapper, response);


        } else {
            filterChain.doFilter(request, response);
        }
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


    }

