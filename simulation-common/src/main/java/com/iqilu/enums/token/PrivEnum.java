package com.iqilu.enums.token;

/**
 * 后台token-priv角色
 *
 * @author zhangyicheng
 * @date 2022/03/26
 */
public enum PrivEnum {

    // 超级管理员
    SUPER("super"),

    // 管理员
    ADMIN("admin"),

    // 普通用户
    USER("user");

    private String type;

    PrivEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
