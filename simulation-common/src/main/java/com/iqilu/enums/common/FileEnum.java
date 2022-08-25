package com.iqilu.enums.common;

/**
 * 文件类型枚举
 *
 * @author zhangyc
 * @date 2022/04/03
 */
public enum FileEnum {

    // xls
    XLS("xls"),

    // xlsx
    XLSX("xlsx"),

    // csv
    CSV("csv");

    private String value;

    FileEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
