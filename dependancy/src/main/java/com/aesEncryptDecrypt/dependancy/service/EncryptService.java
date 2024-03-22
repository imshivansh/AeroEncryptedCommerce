package com.aesEncryptDecrypt.dependancy.service;

import com.aesEncryptDecrypt.dependancy.dto.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class EncryptService {

    private  static final Integer TAG_LENGTH = 128;
    private  static  final Integer  NONCE_LENGTH=12;
    @Autowired
    ObjectMapper objectMapper;

    public  String encrypt(Object requestedObject, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] nonce = new byte[NONCE_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,new GCMParameterSpec(TAG_LENGTH,nonce));

        String orderRequestString = convertOrderRequestToJsonString(requestedObject);
        byte[] encryptedBytes = cipher.doFinal(orderRequestString.getBytes(StandardCharsets.UTF_8));
        byte[] combined  =  new byte[NONCE_LENGTH+encryptedBytes.length];
        System.arraycopy(nonce,0,combined,0,nonce.length);
        System.arraycopy(encryptedBytes,0,combined,nonce.length,encryptedBytes.length);
        return Base64.encodeBase64String(combined);
    }


    private String convertOrderRequestToJsonString(Object orderRequest) {

        try {
            return objectMapper.writeValueAsString(orderRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
