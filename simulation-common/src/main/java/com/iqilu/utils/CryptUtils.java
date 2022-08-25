package com.iqilu.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES 加解密工具
 *
 * @author zhangyc
 * @date 2022/04/03
 */
@Slf4j
public class CryptUtils {

    /**
     * 固定向量
     */
    private static final byte[] IV = "0000000000000000".getBytes();

    /**
     * 加密模式
     */
    private static final String ALG = "AES/CBC/PKCS5Padding";

    /**
     * 加密秘钥
     */
    private static byte[] key;

    /**
     * aes加密
     *
     * @author zhangyicheng
     * @date 2020/09/24
     */
    public static String encrypt(String str, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = common(secret, true);
        byte[] data = cipher.doFinal(str.getBytes());
        return Base64.encodeBase64String(data);
    }

    /**
     * aes解密
     *
     * @author zhangyicheng
     * @date 2020/09/24
     */
    public static String aesDecode(String base64Str, String secret) throws Exception {
        byte[] encryptedBytes = Base64.decodeBase64(base64Str);
        byte[] key = secret.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
        byte[] id = cipher.doFinal(encryptedBytes);
        return new String(id);
    }

    private static Cipher common(String secret, Boolean isEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        key = secret.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
        return cipher;
    }

}
