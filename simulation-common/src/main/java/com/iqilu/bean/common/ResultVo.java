package com.iqilu.bean.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 统一 返回对象类 (不加密返回)
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
    private Integer code;
    private String errmsg;

    public ResultVo() {
    }

    private ResultVo(T data, Integer code, String errmsg) {
        this.data = data;
        this.code = code;
        this.errmsg = errmsg;
    }

    public static <T> ResultVo<T> ok() {
        return new ResultVo<T>(null, 1, "");
    }

    public static <T> ResultVo<T> ok(T data) {
        return new ResultVo<T>(data, 1, "");
    }

    public static <T> ResultVo<T> ok(T data, Integer code) {
        return new ResultVo<T>(data, code, "");
    }

    public static <T> ResultVo<T> ok(T data, Integer code, String errmsg) {
        return new ResultVo<T>(data, code, errmsg);
    }


    public static <T> ResultVo<T> fail(Integer code) {
        return new ResultVo<T>(null, code, "");
    }

    public static <T> ResultVo<T> fail(T data, Integer code) {
        return new ResultVo<T>(data, code, "");
    }

    public static <T> ResultVo<T> fail(T data, Integer code, String errmsg) {
        return new ResultVo<T>(data, code, errmsg);
    }

    public static <T> ResultVo<T> fail(Integer code, String errmsg) {
        return new ResultVo<T>(null, code, errmsg);
    }

    public static <T> ResultVo<T> fail(String msg) {
        return new ResultVo<T>(null, 0, msg);
    }

}