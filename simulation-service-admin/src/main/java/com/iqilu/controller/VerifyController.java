package com.iqilu.controller;

import com.alibaba.fastjson.JSONObject;
import com.iqilu.annotation.verify.PassVerify;
import com.iqilu.bean.common.ResultVo;
import com.iqilu.config.local.UserLocal;
import com.iqilu.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangyc
 * @date 2022-04-03 10:38
 */
@Slf4j
@RestController
@RequestMapping("/verify")
public class VerifyController extends BaseAdminController {

    /**
     * 获取token信息
     *
     * @author zhangyc
     * @date 2022/04/03
     */
    @PassVerify
    @GetMapping("/token_msg")
    public ResultVo<Object> getTokenMsg(String token) {
        JSONObject decode = TokenUtils.decode(token);
        return ResultVo.ok(decode);
    }

    /**
     * 获取附件上传token
     *
     * @author zhangyc
     * @date 2022/04/03
     */
    @GetMapping("/upload_token")
    public ResultVo<String> getUploadToken() {
        Integer orgId = UserLocal.get().getOrgid();
        Integer userId = UserLocal.get().getId();
        String uploadToken = TokenUtils.getUploadToken(userId, super.uploadSecret, orgId.toString());
        return ResultVo.ok(uploadToken);
    }

    /**
     * 第三方页面跳转认证
     *
     * @author zhangyc
     * @date 2021/12/13
     */
    @PassVerify
    @GetMapping("/jump_third")
    public ModelAndView verifyAdminWeb(String token, HttpServletResponse response) {
        log.info("[jump_third传入] -> {}", token);
        Boolean isPass = TokenUtils.verifyAdminWeb(token, super.jumpSecret);
        if (isPass) {
            String adminToken = TokenUtils.getAdminToken(token, super.adminSecret, "");
            log.info("[jump_third带出] -> {}", adminToken);
            Cookie cookie = new Cookie("simulationToken", String.valueOf(adminToken));
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ModelAndView("redirect:" + adminWebLink);
        } else {
            throw new RuntimeException("令牌验证不通过");
        }
    }


}
