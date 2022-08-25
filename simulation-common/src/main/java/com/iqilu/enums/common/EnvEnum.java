package com.iqilu.enums.common;

/**
 * 环境
 *
 * @author zhangyicheng
 * @date 2020/08/28
 */
public enum EnvEnum {//枚举类型

    // 生产环境
    PROD("prod"),

    // 测试环境
    TEST("test"),

    // 开发环境
    DEV("dev");

    private String value;

    EnvEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
