package com.iqilu.annotation.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用: 后台-需要登陆才能操作
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
public @interface AdminVerify {
    boolean required() default true;
}
