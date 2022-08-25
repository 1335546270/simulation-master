package com.iqilu.utils;

import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * header相关处理
 *
 * @author zhangyicheng
 * @date 2020/09/27
 */
public class HeaderUtils {

    /**
     * 修改header信息，key-value键值对儿加入到header中
     *
     * @author zhangyicheng
     * @date 2020/09/27
     */
    public static void reflectSetParam(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
            // 设置Key
            o2.setValue(key).setString(value);
            // 添加新Key
///            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
