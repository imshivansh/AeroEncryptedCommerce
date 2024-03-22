package com.aesEncryptDecrypt.dependancy.controller;

import com.aesEncryptDecrypt.dependancy.dto.OrderRequest;
import com.aesEncryptDecrypt.dependancy.service.EncryptService;
import com.aesEncryptDecrypt.dependancy.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/encrypt")
public class EncryptController {

    @Autowired
    EncryptService encryptService;

    @PostMapping
    public  String encrypt(@RequestBody Object orderRequest) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey secretKey = AppConstants.secretKey;
        return encryptService.encrypt(orderRequest,secretKey);
    }
}
