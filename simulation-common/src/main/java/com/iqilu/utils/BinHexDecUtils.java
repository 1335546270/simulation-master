package com.iqilu.utils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * ascii, 二进制, 十进制, 十六进制转换工具类
 *
 * @author zhangyicheng
 * @date 2021/04/18
 */
public class BinHexDecUtils {

    /**
     * ASCII转16进制
     *
     * @author zhangyicheng
     * @date 2021/04/18
     */
    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char aChar : chars) {
            hex.append(Integer.toHexString((int) aChar));
        }

        return hex.toString();
    }

    /**
     * 16进制转ASCII
     *
     * @author zhangyicheng
     * @date 2021/04/18
     */
    public static String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        int step = 2;
        for (int i = 0; i < hex.length() - 1; i += step) {
            // 获取16进制
            String output = hex.substring(i, (i + 2));
            // 将16进制转换为十进制
            int decimal = Integer.parseInt(output, 16);
            // 将小数点转换为字符串
            sb.append((char) decimal);
        }

        return sb.toString();
    }

    /**
     * 2进制转16进制
     *
     * @author zhangyicheng
     * @date 2021/03/22
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            stringBuilder.append(i).append(":");

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv).append(";");
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制转2进制
     *
     * @author zhangyicheng
     * @date 2021/03/22
     */
    public static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

    /**
     * 16进制转10进制
     *
     * @author zhangyicheng
     * @date 2021/04/18
     */
    public static int covert(String content) {
        int number = 0;
        content = content.toUpperCase();
        String[] highLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>(16);
        int size = 9;
        for (int i = 0; i <= size; i++) {
            map.put(i + "", i);
        }
        int val = 10;
        for (int j = 10; j < highLetter.length + val; j++) {
            map.put(highLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            number += map.get(str[i]) * Math.pow(16, str.length - 1 - i);
        }
        return number;
    }

    /**
     * 10进制转16进制
     *
     * @author zhangyicheng
     * @date 2021/03/22
     */
    public static String toHex(int num, int totalLenght) {
        String str = Integer.toHexString(num);
        str = splicingZero(str, totalLenght);
        return str;
    }

    /**
     * 字符串前面补零操作
     *
     * @author zhangyicheng
     * @date 2021/03/22
     */
    public static String splicingZero(String str, int totalLenght) {
        int strLenght = str.length();
        StringBuilder strReturn = new StringBuilder(str);
        for (int i = 0; i < totalLenght - strLenght; i++) {
            strReturn.insert(0, "0");
        }
        return strReturn.toString();
    }

}
