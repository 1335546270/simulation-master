package com.iqilu.config;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.iqilu.annotation.verify.PassVerify;
import com.iqilu.annotation.verify.ThirdVerify;
import com.iqilu.bean.common.ResultVo;
import com.iqilu.config.local.UserLocal;
import com.iqilu.config.local.UserTokenMsg;
import com.iqilu.controller.BaseAdminController;
import com.iqilu.enums.common.CommonEnum;
import com.iqilu.utils.DateTimeUtils;
import com.iqilu.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;

/**
 * 方法拦截器
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Slf4j
public class AuthenticationInterceptor extends BaseAdminController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String reqMethod = request.getMethod();
        String reqUrl = request.getRequestURL().toString();
        response.setContentType("application/json; charset=utf-8");

        /* 如果不是映射到方法直接通过 */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = TokenUtils.getToken(request);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        /* 签名验证 */
        if (!this.verifySign(request, response)) {
            return false;
        }

        /* 设置信息进Thread */
        if (StringUtils.isNotBlank(token)) {
            JSONObject decode = TokenUtils.decode(token);
            UserTokenMsg userTokenMsg = JSON.parseObject(String.valueOf(decode), UserTokenMsg.class);
            UserLocal.set(userTokenMsg);
        } else {
            UserTokenMsg userTokenMsg = new UserTokenMsg();
            Integer orgId = TokenUtils.getOrgId(request);
            userTokenMsg.setOrgid(orgId);
            UserLocal.set(userTokenMsg);
        }

        /* 第三方验证 */
        if (method.isAnnotationPresent(ThirdVerify.class)) {
            ThirdVerify thirdVerify = method.getAnnotation(ThirdVerify.class);
            if (thirdVerify.required()) {
                log.info("地址[third]: {}, 方法: {}, 第三方调用", reqUrl, reqMethod);
                return true;
            }
        }

        /* 跳过验证 */
        if (method.isAnnotationPresent(PassVerify.class)) {
            PassVerify passVerify = method.getAnnotation(PassVerify.class);
            if (passVerify.required()) {
                log.info("地址[pass]: {}, 方法: {}", reqUrl, reqMethod);
                return true;
            }
        }

        /* 不存在该地址 */
        if (reqUrl.contains("/error")) {
            ResultVo<Object> res = ResultVo.fail("", 0, "404");
            this.closeStream(response, res);
            return false;
        }

        /* token验证 */
        return this.verifyToken(response, token, super.adminSecret, reqUrl);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
        // 清除后台用户信息
        UserLocal.set(null);
        UserLocal.clear();
    }

    /**
     * 验证token
     *
     * @author zhangyicheng
     * @date 2021/02/09
     */
    private Boolean verifyToken(HttpServletResponse response, String token, String secret, String reqUrl) throws Exception {
        if (token == null) {
            log.error("地址 -> {}", reqUrl);
            this.closeStream(response, ResultVo.fail("", 0, "无token, 请重新登录."));
            return false;
        }
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            log.info("地址[manager] -> {}", reqUrl);
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            this.closeStream(response, ResultVo.fail("", 0, "token验证不通过, 检查token是否合法."));
            return false;
        }

        return true;
    }

    /**
     * 验证url参数签名
     *
     * @author zhangyc
     * @date 2021/11/23
     */
    private Boolean verifySign(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 如果含这个路径则不验证签名
        if (request.getRequestURL().toString().contains("/jump_third")) {
            return true;
        }

        String sign = request.getParameter("sign");
        String noncestr = request.getParameter("noncestr");
        String orgid = request.getParameter("orgid");
        String timestamp = request.getParameter("timestamp");
        String e = request.getParameter("e");
        int nowTimestamp = DateTimeUtils.nowSec();

        /* 验证时间戳是否超期 默认30分钟 */
        String signVerify = DigestUtil.md5Hex(noncestr + timestamp + orgid + super.paramSecret);
        if (!signVerify.equals(sign)) {
            ResultVo<Object> res = ResultVo.fail("", 0, "签名验证不通过");
            this.closeStream(response, res);
            log.error("签名不通过: sign -> {}, noncestr -> {}, orgid -> {}, timestamp -> {}, nowTimestamp -> {}",
                    sign, noncestr, orgid, timestamp, nowTimestamp);
            return false;
        }
        if (!CommonEnum.NO_VERIFY.getValue().equals(e)) {
            if (Integer.parseInt(timestamp) + 60 * 30 < nowTimestamp) {
                ResultVo<Object> res = ResultVo.fail("", 0, "请求已过期, 签名验证不通过");
                this.closeStream(response, res);
                log.error("请求已过期, 签名验证不通过: sign -> {}, noncestr -> {}, orgid -> {}, timestamp -> {}, nowTimestamp -> {}",
                        sign, noncestr, orgid, timestamp, nowTimestamp);
                return false;
            }
        }

        return true;
    }

    /**
     * 关闭流
     *
     * @author zhangyicheng
     * @date 2021/02/18
     */
    private void closeStream(HttpServletResponse response, ResultVo<Object> res) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(res));
        out.flush();
        out.close();
    }

}
