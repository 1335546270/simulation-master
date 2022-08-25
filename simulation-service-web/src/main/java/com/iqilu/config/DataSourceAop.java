package com.iqilu.config;

import com.iqilu.datasource.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面, 读写分离切库
 *
 * @author zhangyicheng
 * @date 2022/03/17
 */
@Aspect
@Component
public class DataSourceAop {

    /**
     * 读切面（条件1 && 条件2）
     * 条件1：没有Master注解 条件2：com.iqilu.service.impl包下 任意类 的select* 或者get*方法
     */
    @Pointcut(
            "!@annotation(com.iqilu.annotation.datasource.Master) "
                    + "&& (execution(* com.iqilu.service.impl..*.select*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.get*(..)))")
    public void readPointcut() {
    }


    /**
     * 写切面
     */
    @Pointcut(
            "@annotation(com.iqilu.annotation.datasource.Master) "
                    + "|| execution(* com.iqilu.service.impl..*.insert*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.add*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.update*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.edit*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.delete*(..)) "
                    + "|| execution(* com.iqilu.service.impl..*.remove*(..))")
    public void writePointcut() {
    }

    /**
     * Before方法，设置ThreadLocal里的一个变量为slave
     */
    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    /**
     * Before方法，设置ThreadLocal里的一个变量为master
     */
    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }

}