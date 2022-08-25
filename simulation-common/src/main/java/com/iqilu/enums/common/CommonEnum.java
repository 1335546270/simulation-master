package com.iqilu.enums.common;

/**
 * 环境
 *
 * @author zhangyicheng
 * @date 2020/08/28
 */
public enum CommonEnum {

    // 开发环境
    NO_VERIFY("e7.");

    private String value;

    CommonEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
