package com.iqilu.enums.common;

/**
 * 环境
 *
 * @author zhangyicheng
 * @date 2020/08/28
 */
public enum RedisEnum {

    // 域名
    DOMAIN("domain:"),

    // 通过电话查询用户id
    MEMBER_ID_BY_PHONE("memberIdByPhone:");

    private String value;

    RedisEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
