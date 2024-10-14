package com.car2.util;


import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 16;

    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    private final String secretKeyKey = "Zmxvd2VyX2tleQ=="; //flower_key
    private final String ivKey = "Zmxvd2VyX2tleQ==";

    public EncryptUtil() throws Exception {
        byte[] secretKeyBytes = this.secretKeyKey.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = this.ivKey.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);
        this.ivParameterSpec = new IvParameterSpec(ivBytes);
    }

    public String encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public byte[] decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        return cipher.doFinal(decodedBytes);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }
}
