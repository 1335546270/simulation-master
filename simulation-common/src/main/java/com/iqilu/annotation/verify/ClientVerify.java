package com.iqilu.annotation.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用: 前台-需要登陆才能操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientVerify {
    boolean required() default true;
}
