package com.aerospike.encryptDecrypt.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class ConstantSecretKey {
    private static volatile ConstantSecretKey constants;
    private final SecretKey secretKey;

    private ConstantSecretKey(){
        try {
            secretKey = generateSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static ConstantSecretKey getConstants() throws NoSuchAlgorithmException {
        if(constants==null){
            synchronized (ConstantSecretKey.class){
                if(constants==null){
                    constants = new ConstantSecretKey();
                }
            }
        }
        return  constants;

    }
    private  SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public SecretKey getSecretKey(){
        return  this.secretKey;
    }

}
