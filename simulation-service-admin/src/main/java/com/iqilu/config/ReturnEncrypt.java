package com.iqilu.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iqilu.bean.common.ResultEncryptVo;
import com.iqilu.enums.common.CommonEnum;
import com.iqilu.utils.CryptUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 统一的用户数据返回处理
 * 主要用于对返回的json结果进行统一的加密处理
 * 只对返回的Response实例处理加密
 * @author zhangyc
 */
@Slf4j
@ControllerAdvice
public class ReturnEncrypt implements ResponseBodyAdvice {

    @Value(value = "${secret.response_secret}")
    public String responseSecret;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(request instanceof ServletServerHttpRequest)) {
            return body;
        }
        if (!(response instanceof ServletServerHttpResponse)) {
            return body;
        }
        String e = "0";
        HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
        try {
            e = httpRequest.getParameter("e");
        } catch (Exception exception) {
            return body;
        }

        /*
         * 构造秘钥
         */
        if (!ObjectUtils.isEmpty(e) && CommonEnum.NO_VERIFY.getValue().equals(e)) {
            return body;
        }

        /* 加密处理, 指堆返回的Response实例进行加密处理 */
        if (body instanceof ResultEncryptVo) {
            if (((ResultEncryptVo<?>) body).getCode() != 1) {
                return body;
            }

            httpResponse.setHeader("encrypt", "aes");
            httpResponse.setHeader("content-Type", "text/plain");
            httpResponse.setHeader("Access-Control-Expose-Headers", "encrypt");
            String bodyString = JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);

            try (OutputStream stream = response.getBody()) {
                stream.write(CryptUtils.encrypt(bodyString, responseSecret).getBytes(StandardCharsets.UTF_8));
                stream.flush();
                return null;
            } catch (IOException e1) {
                log.error("出错了: ", e1);
                e1.printStackTrace();
            }
        }

        return body;
    }
}
