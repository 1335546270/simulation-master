package com.iqilu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author zhangyicheng
 * @date 2021/04/18
 */
public class EncryptUtils {

    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return 返回固定长度字符串, MD5加密后的字符串
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getMd5(String str) {

        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * SHA1加密，参数是由url、jsapi_ticket、noncestr、timestamp组合而成
     *
     * @author zhangyicheng
     * @date 2021/04/09
     */
    public static String sha1(String str) {
        try {
            // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexStr = new StringBuilder();
            // 字节数组转换为 十六进制 数
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
