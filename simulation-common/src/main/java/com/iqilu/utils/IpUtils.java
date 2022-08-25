package com.iqilu.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP方法
 *
 * @author cool
 */
public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String ip = null;

        ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }

        if (null == ip) {
            return "error_ip";
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
