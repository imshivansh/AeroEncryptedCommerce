package com.aerospike.encryptDecrypt.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AppConstants {

  private final static String secretString = "werftghrreqyrtyq";
  private static final byte[] bytes= secretString.getBytes(StandardCharsets.UTF_8);
  public static final SecretKey secretKey = new SecretKeySpec(bytes,"AES");
}
