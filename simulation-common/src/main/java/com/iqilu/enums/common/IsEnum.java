package com.iqilu.enums.common;

/**
 * 用户判断是否
 *
 * @author zhangyicheng
 * @date 2022/03/19
 */
public enum IsEnum {

    // 是
    IS_YES(1),

    // 否
    IS_NO(0);

    private Integer value;

    IsEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
