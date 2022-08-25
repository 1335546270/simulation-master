package com.iqilu.config;

import com.iqilu.bean.common.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(Exception.class)
    public Object interceptException(Exception e, HttpServletRequest request) {

        String reqContentType = request.getContentType();
        String reqUrl = request.getRequestURL().toString();

        log.error("---------------service-admin-----------------");
        log.error("url: {}, type: {}, 捕获异常 ↓ ", reqUrl, reqContentType, e);
        return ResultVo.fail(0, "出错: " + e);
    }
}
