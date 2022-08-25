package com.iqilu.controller;

import com.iqilu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基类
 *
 * @author zhangyicheng
 * @date 2021/02/09
 */
@RestController
public class BaseController {

    @Autowired
    public RedisUtils stringRedisTemplate;

    @Value(value = "${secret.param_secret}")
    public String paramSecret;

    @Value(value = "${secret.client_secret}")
    public String clientSecret;


    @Value(value = "${client_get_user_id_link}")
    public String clientGetUserIdLink;

    @Value(value = "${client_get_user_info_link}")
    public String clientGetUserInfoLink;

    @Value(value = "${message_push}")
    public String messagePush;


    @Value(value = "${message_on_off}")
    public String messageOnOff;

}
