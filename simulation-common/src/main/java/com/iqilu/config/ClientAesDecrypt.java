package com.iqilu.config;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 客户端AES加解密
 *
 * @author zhangyicheng
 * @date 2020/09/24
 */
public class ClientAesDecrypt {

    /**
     * IV: 偏移量
     */
    private static final byte[] IV = "0000000000000000".getBytes();

    /**
     * AES-openId解密密钥
     */
    private static final String AES_SECRET = "f9851fac6a5fa7f2500099c8715cc436";

    /**
     * 加密模式
     */
    private static final String ALG = "AES/CBC/PKCS5Padding";

    private static final String AES = "AES";

    /**
     * AES解密
     *
     * @author zhangyicheng
     * @date 2020/09/24
     */
    public static String aesDecode(String str) throws Exception {

        byte[] encryptedBytes = Base64.decodeBase64(str);
        byte[] key = ClientAesDecrypt.AES_SECRET.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
        byte[] id = cipher.doFinal(encryptedBytes);
        return new String(id);
    }

    /**
     * AES加密
     *
     * @author zhangyc
     * @date 2022/04/03
     */
    public static String aesEncode(String str) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(AES_SECRET.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV));
        byte[] data = cipher.doFinal(str.getBytes());
        return Base64.encodeBase64String(data);
    }

}
