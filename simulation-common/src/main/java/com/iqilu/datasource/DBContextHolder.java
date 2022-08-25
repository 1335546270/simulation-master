package com.iqilu.datasource;

import com.iqilu.enums.datasource.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据源切换
 *
 * @author zhangyc
 * @date 2021-12-14 17:00
 */
@Slf4j
public class DBContextHolder {

    /**
     * 本地线程变量, 保存当前线程对应的数据库信息
     *
     * @author zhangyc
     * @date 2022/04/02
     */
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.debug("[数据源切换到] -> master");
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE);
        log.debug("[数据源切换到] -> slave");
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}