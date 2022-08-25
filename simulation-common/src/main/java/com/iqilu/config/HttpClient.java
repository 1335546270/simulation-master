package com.iqilu.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTPClient连接池 todo: 该类待完善
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Slf4j
@Component
public class HttpClient {

    /**
     * 池化管理
     */
    private static PoolingHttpClientConnectionManager poolConnManager = null;//初始化连接池
    private static CloseableHttpClient httpClient;
    /**
     * 请求器配置
     */
    private static RequestConfig requestConfig;

    static {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());

            // 配置同时支持HTTP和HTTPS协议
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 设置最大连接数
            poolConnManager.setMaxTotal(200);
            // 设置路由
            poolConnManager.setDefaultMaxPerRoute(50);
            // 设置默认超时限制初始化requestConfig
            int socketTimeout = 3000;
            int connectTimeout = 3000;
            int connectionRequestTimeout = 3000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpClient = getConnection();
            log.info("HttpClient init ..");
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建HttpClient对象
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    private static CloseableHttpClient getConnection() {
        return HttpClients.custom().setConnectionManager(poolConnManager)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }


    /**
     * get请求
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            log.info("请求(httpGet) -> {}", url);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * get请求
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String httpGet(String url, String orgId) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("orgId", orgId);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            log.info("请求(httpGet) -> {}, 机构 -> {}", url, orgId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * get - 请求创奇
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String doGetCq(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Dubbo-Attachments", "appkey=comment,appsecret=d2ff9b3583507c7e48ed3b1769b89f7d");
        httpGet.setHeader("Accept", "application/json,*/*");
        CloseableHttpResponse response = null;
        String result = "";
        long start = System.currentTimeMillis();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            log.info("请求(doGetCq) -> {}", url);
        } catch (IOException e) {
            long end = System.currentTimeMillis() - start;
            log.error("请求报错(doGetCq) -> {}, 耗时 -> {}ms", url, end);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * get - 请求创奇
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String doGetCq(String url, String orgId, String token) {

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Dubbo-Attachments", "appkey=comment,appsecret=d2ff9b3583507c7e48ed3b1769b89f7d");
        httpGet.setHeader("Accept", "application/json,*/*");
        httpGet.setHeader("orgid", orgId);
        httpGet.setHeader("CQ-TOKEN", token);
        CloseableHttpResponse response = null;
        String result = "";
        long start = System.currentTimeMillis();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            log.info("请求(doGetCq) -> {}, 机构 -> {}, 令牌 -> {}", url, orgId, token);
        } catch (IOException e) {
            long end = System.currentTimeMillis() - start;
            log.error("请求报错(doGetCq) -> {}, 耗时 -> {}ms", url, end);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * HttpPost请求
     *
     * @author zhangyicheng
     * @date 2020/06/15
     */
    public static String doPost(String url, Map<String, Object> paramsMap) {
        CloseableHttpResponse closeableHttpResponse = null;
        //配置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .setRedirectsEnabled(true)
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        //装配post请求参数
        List<NameValuePair> list = new ArrayList<>();
        for (String key : paramsMap.keySet()) {
            list.add(new BasicNameValuePair(key, String.valueOf(paramsMap.get(key))));
        }
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);

            //执行 post请求
            closeableHttpResponse = httpClient.execute(httpPost);
            String strRequest = "";
            if (null != closeableHttpResponse) {
                if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = closeableHttpResponse.getEntity();
                    strRequest = EntityUtils.toString(httpEntity);
                } else {
                    strRequest = "Error Response" + closeableHttpResponse.getStatusLine().getStatusCode();
                }
            }
            log.info("请求(doPost) -> {}, param -> {}", url, paramsMap.toString());
            return strRequest;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "协议异常";
        } catch (ParseException e) {
            e.printStackTrace();
            return "解析异常";
        } catch (IOException e) {
            e.printStackTrace();
            return "传输异常";
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
