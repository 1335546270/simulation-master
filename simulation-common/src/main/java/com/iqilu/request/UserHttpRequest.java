package com.iqilu.request;

import com.alibaba.fastjson.JSONObject;
import com.iqilu.bean.vo.user.UserInfoVo;
import com.iqilu.config.HttpClient;
import com.iqilu.bean.vo.user.HttpResultVo;
import com.iqilu.enums.common.IsEnum;
import com.iqilu.enums.common.RedisEnum;
import com.iqilu.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 第三方 - 用户公共平台数据获取
 *
 * @author zhangyicheng
 * @date 2020/07/11
 */
@Slf4j
@Component
public class UserHttpRequest {

    /**
     * 通过用户IDs获取用户信息集合
     *
     * @param idList 用户id集合
     * @author zhangyicheng
     * @date 2020/07/11
     */
    public static HttpResultVo getUserInfoListById(String idList, Integer orgId, String requestLink) {
        HttpResultVo httpResult = new HttpResultVo();
        if (idList.length() > 0) {
            try {
                String url = requestLink + "/" + orgId + "/" + idList;
                String userInfoList = HttpClient.doGetCq(url);
                httpResult = JSONObject.parseObject(userInfoList, HttpResultVo.class);
            } catch (Exception e) {
                log.error("impl.getUserInfo: Exception !!", e);
            }
        }
        return httpResult;
    }

    /**
     * 通过电话获取用户ID
     *
     * @author zhangyicheng
     * @date 2020/07/15
     */
    public static String getUserIdByPhone(String userPhone, Integer orgId, String requestLink) {
        String rdsKey = RedisEnum.DOMAIN.getValue() + orgId + ":" + "value";
        JSONObject userJson;
        String userId = null;
        try {
            String memberId = RedisUtils.get(rdsKey);
            log.debug("get > {} -> {}", rdsKey, memberId);
            if (memberId != null) {
                return memberId;
            }
            String url = requestLink + "/" + orgId + "/" + userPhone;
            String userInfo = HttpClient.doGetCq(url);
            userJson = JSONObject.parseObject(userInfo);
            if ((Integer) userJson.get("code") > 0) {
                userId = JSONObject.parseObject(userJson.get("data").toString()).getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getUserIdByPhone-Exception", e);
        }

        /* redis 不存在, 插入redis中 */
        if (StringUtils.isNotEmpty(userId)) {
            RedisUtils.set(rdsKey, userId);
            RedisUtils.expire(rdsKey, 10, TimeUnit.DAYS);
            log.debug("set > {} -> {}", rdsKey, userId);
        }
        return userId;
    }

}
