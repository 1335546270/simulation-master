package com.iqilu.bean.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 统一 返回对象类 (加密返回)
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Data
public class ResultEncryptVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
    private Integer code;
    private String errmsg;

    public ResultEncryptVo() {
    }

    private ResultEncryptVo(T data, Integer code, String errmsg) {
        this.data = data;
        this.code = code;
        this.errmsg = errmsg;
    }

    public static <T> ResultEncryptVo<T> ok() {
        return new ResultEncryptVo<T>(null, 1, "");
    }

    public static <T> ResultEncryptVo<T> ok(T data) {
        return new ResultEncryptVo<T>(data, 1, "");
    }

    public static <T> ResultEncryptVo<T> ok(T data, Integer code) {
        return new ResultEncryptVo<T>(data, code, "");
    }

    public static <T> ResultEncryptVo<T> ok(T data, Integer code, String errmsg) {
        return new ResultEncryptVo<T>(data, code, errmsg);
    }


    public static <T> ResultEncryptVo<T> fail(Integer code) {
        return new ResultEncryptVo<T>(null, code, "");
    }

    public static <T> ResultEncryptVo<T> fail(T data, Integer code) {
        return new ResultEncryptVo<T>(data, code, "");
    }

    public static <T> ResultEncryptVo<T> fail(T data, Integer code, String errmsg) {
        return new ResultEncryptVo<T>(data, code, errmsg);
    }

    public static <T> ResultEncryptVo<T> fail(Integer code, String errmsg) {
        return new ResultEncryptVo<T>(null, code, errmsg);
    }

    public static <T> ResultEncryptVo<T> fail(String msg) {
        return new ResultEncryptVo<T>(null, 0, msg);
    }

}