package com.aerospike.encryptDecrypt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class AesGcmService implements IEncryptDecrypt{
    @Autowired
    ObjectMapper objectMapper;
    public AesGcmService() {
    }

    private  static  final Integer  NONCE_LENGTH=12;
    private static  final Integer TAG_LENGTH=128;


    public String encrypt(Object payload, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        byte[]nonce  = new byte[NONCE_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);


        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey,new GCMParameterSpec(TAG_LENGTH,nonce));
        String jsonObjectString = getObjectAsJsonString(payload);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(payload);
//        objectOutputStream.close();

        byte[]encryptedCipher = cipher.doFinal(jsonObjectString.getBytes(StandardCharsets.UTF_8));
        byte[]combined= new byte[nonce.length+encryptedCipher.length];
        System.arraycopy(nonce,0,combined,0,nonce.length);
        System.arraycopy(encryptedCipher,0,combined,nonce.length,encryptedCipher.length);

        return Base64.encodeBase64String(combined);
    }



    private String getObjectAsJsonString(Object object){
//        ObjectMapper objectMapper = new ObjectMapper();
        try {
          return   objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public String  decrypt(SecretKey secretKey, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] combined = Base64.decodeBase64(data);
        byte[] nonce = new byte[NONCE_LENGTH];

        System.arraycopy(combined, 0, nonce, 0, nonce.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH, nonce));
        byte[] decryptedCipher = cipher.doFinal(combined,NONCE_LENGTH,combined.length-NONCE_LENGTH);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedCipher);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//        OrderRequest orderRequest = (OrderRequest) objectInputStream.readObject();
        return  new String(decryptedCipher, StandardCharsets.UTF_8);
    }

}
