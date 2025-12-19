package com.easy.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author arshe
 * AES密钥生成器
 */
public class AESKeyGenerator {

    private static final String PBK_ALGORITHM = "PBKDF2WithHmacSHA256";

    private static final String AEX_ALGORITHM = "AES";

    /**
     * 密钥长度
     */
    private static final int keyLength = 256;

    /**
     * 迭代次数
     */
    private static final int iterationCount = 65535;


    public static String generateKey(byte[] salt, String originalKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 1. 使用PBKDF2派生密钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBK_ALGORITHM);
        KeySpec spec = new PBEKeySpec(originalKey.toCharArray(), salt, iterationCount, keyLength);
        byte[] derivedKeyBytes = factory.generateSecret(spec).getEncoded();

        // 2. 将派生的字节转换为AES密钥对象
        SecretKeySpec aesKey = new SecretKeySpec(derivedKeyBytes, AEX_ALGORITHM);

        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }

    public static byte[] generateSaltByLong(long num) {
        return ByteBuffer.allocate(Long.BYTES).putLong(num).array();
    }
}
