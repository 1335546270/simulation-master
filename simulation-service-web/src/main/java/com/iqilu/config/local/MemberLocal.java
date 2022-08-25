package com.iqilu.config.local;


/**
 * 用户thread信息
 *
 * @author zhangyc
 * @date 2022/04/03
 */
public class MemberLocal {
    private MemberLocal(){}

    private static ThreadLocal<MemberTokenMsg> local = new ThreadLocal<>();

    public static void set(MemberTokenMsg user){
        local.set(user);
    }

    public static MemberTokenMsg get(){
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
