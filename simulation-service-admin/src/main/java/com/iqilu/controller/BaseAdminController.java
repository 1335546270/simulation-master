package com.iqilu.controller;

import com.iqilu.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基类
 *
 * @author zhangyicheng
 * @date 2021/02/09
 */
@Slf4j
@RestController
public class BaseAdminController {

    @Autowired
    public RedisUtils stringRedisTemplate;

    @Value(value = "${secret.param_secret}")
    public String paramSecret;

    @Value(value = "${secret.admin_secret}")
    public String adminSecret;

    @Value(value = "${secret.response_secret}")
    public String responseSecret;

    @Value(value = "${secret.jump_secret}")
    public String jumpSecret;

    @Value(value = "${secret.upload_secret}")
    public String uploadSecret;

    @Value(value = "${secret.request_secret}")
    public String requestSecret;


    @Value(value = "${client_get_user_id_link}")
    public String clientGetUserIdLink;

    @Value(value = "${client_get_user_info_link}")
    public String clientGetUserInfoLink;

    @Value(value = "${message_push}")
    public String messagePush;

    @Value(value = "${admin_get_user_info}")
    public String adminGetUserInfo;


    @Value(value = "${message_on_off}")
    public String messageOnOff;

    @Value(value = "${admin_web_link}")
    public String adminWebLink;


}
