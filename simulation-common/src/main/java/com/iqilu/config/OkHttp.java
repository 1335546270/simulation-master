package com.iqilu.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * okHttp
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Slf4j
public class OkHttp {

    /**
     * post请求
     *
     * @author zhangyc
     * @date 2022/04/03
     */
    public static String okPost(String url, Map<String, Object> paramsMap) {
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        String resStr = null;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestbody = new FormBody.Builder().add("", JSON.toJSONString(paramsMap)).build();
        try {
            Request request = new Request.Builder().post(requestbody).url(url).build();
            Response response = mOkHttpClient.newCall(request).execute();
            resStr = response.body() != null ? response.body().string() : null;
            log.info("请求(okPost) -> {}, 参数 -> {}", url, paramsMap.toString());
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                try {
                    Request request = new Request.Builder().post(requestbody).url(url).build();

                    Response response = mOkHttpClient.newCall(request).execute();
                    resStr = response.body() != null ? response.body().string() : null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
            return resStr;
        }
        return resStr;
    }

    /**
     * post请求 - 请求创奇
     *
     * @author zhangyicheng
     * @date 2020/11/18
     */
    public static String okPostCq(String url, Map<String, Object> paramsMap) {
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        String resStr = null;
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestbody = RequestBody.create(mediaType, JSON.toJSONString(paramsMap));
        try {
            Request request = new Request.Builder().
                    addHeader("Dubbo-Attachments", "appkey=comment,appsecret=d2ff9b3583507c7e48ed3b1769b89f7d").
                    addHeader("Accept", "application/json,*/*").
                    post(requestbody).url(url).build();
            Response response = mOkHttpClient.newCall(request).execute();
            resStr = response.body() != null ? response.body().string() : "";
            log.info("请求(okPostCq) -> {}, 参数 -> {}", url, paramsMap.toString());
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                try {
                    Request request = new Request.Builder().post(requestbody).url(url).build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    resStr = response.body() != null ? response.body().string() : "";
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
            return resStr;
        }
        return resStr;
    }
}