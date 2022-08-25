package com.iqilu.config.local;


/**
 * 用户thread信息
 *
 * @author zhangyc
 * @date 2022/04/03
 */
public class UserLocal {
    private UserLocal(){}

    private static ThreadLocal<UserTokenMsg> local = new ThreadLocal<>();

    public static void set(UserTokenMsg user){
        local.set(user);
    }

    public static UserTokenMsg get(){
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
