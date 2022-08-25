package com.iqilu.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 公共工具类
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
public class CommonUtils {

    /**
     * 获取UUID
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 设置Float保留的位数
     *
     * @param number 输入的Float值
     * @param digit  格式化成Float的格式
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static float floatDigit(float number, String digit) {
        // 设置保留位数
        DecimalFormat df = new DecimalFormat(digit);
        return number;
    }

    /**
     * 设置double保留的位数
     *
     * @param number 输入的double值
     * @param digit  格式化成double的格式
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static double doubleDigit(double number, String digit) {
        // 设置保留位数
        DecimalFormat df = new DecimalFormat(digit);
        return number;
    }

    /**
     * 判断是否为空
     *
     * @author zhangyicheng
     * @date 2021/04/18
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断是否为json格式
     *
     * @author zhangyicheng
     * @date 2021/04/16
     */
    public static boolean isJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;

        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        return isJsonObject || isJsonArray;
    }

    /**
     * 按秒休眠.
     *
     * @param seconds 秒数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void sleepSec(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按毫秒数休眠.
     *
     * @param ms 毫秒数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void sleepMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将id字符串转成List<Integer>
     *
     * @author zhangyicheng
     * @date 2021/10/20
     */
    public static List<Integer> getContentIntegerList(String sign, String idListString) {
        String[] split = idListString.trim().split(sign);
        List<Integer> idList = new ArrayList<>();
        for (String s : split) {
            idList.add(Integer.valueOf(s));
        }
        return idList;
    }

    /**
     * 将id字符串转成List<Integer>
     *
     * @author zhangyicheng
     * @date 2021/10/20
     */
    public static List<String> getContentStringList(String sign, String idListString) {
        String[] split = idListString.trim().split(sign);
        List<String> idList = new ArrayList<>();
        for (String s : split) {
            idList.add(s.trim());
        }
        return idList;
    }

}
