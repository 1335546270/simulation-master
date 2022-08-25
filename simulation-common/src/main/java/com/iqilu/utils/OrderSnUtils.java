package com.iqilu.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 生成订单号
 *
 * @author zhenyuemeng
 * @date 2020/11/03
 */
@Slf4j
public class OrderSnUtils {

    private static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    private static final AtomicInteger SEQ = new AtomicInteger(1000);
    private static final DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");
    private volatile static String IP_SUFFIX = null;

    /**
     * AtomicInteger内使用的是CAS自旋转锁 保证可见性的同时也保证原子性
     *
     * @return
     */
    public static String generateOrderNo() {
        LocalDateTime dateTime = LocalDateTime.now(ZONE_ID);
        if (SEQ.intValue() > 9990) {
            SEQ.getAndSet(10000);
        }
        return dateTime.format(DF_FMT_PREFIX) + getLocalIpSuffix() + SEQ.getAndIncrement();
    }

    /**
     * 对不为null的逻辑加同步锁(双向校验锁，整体是一种安全的单例模式)
     *
     * @author zhenyuemeng
     * @date 2020/11/03
     */
    private static String getLocalIpSuffix() {
        if (null != IP_SUFFIX) {
            return IP_SUFFIX;
        }
        try {
            synchronized (OrderSnUtils.class) {
                if (null != IP_SUFFIX) {
                    return IP_SUFFIX;
                }
                InetAddress addr = InetAddress.getLocalHost();
                String hostAddress = addr.getHostAddress();
                if (null != hostAddress && hostAddress.length() > 4) {
                    String ipSuffix = hostAddress.trim().split("\\.")[3];
                    if (ipSuffix.length() == 2) {
                        IP_SUFFIX = ipSuffix;
                        return IP_SUFFIX;
                    }
                    ipSuffix = "0" + ipSuffix;
                    IP_SUFFIX = ipSuffix.substring(ipSuffix.length() - 2);
                    return IP_SUFFIX;
                }
                IP_SUFFIX = RandomUtils.nextInt(10, 20) + "";
                return IP_SUFFIX;
            }
        } catch (Exception e) {
            log.error("获取IP失败: ", e);
            IP_SUFFIX = RandomUtils.nextInt(10, 20) + "";
            return IP_SUFFIX;
        }
    }

    /**
     * 测试
     *
     * @date 2020/11/03
     */
    public static void main(String[] args) {
        List<String> orderNos = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0, 8000).parallel().forEach(i -> {
            orderNos.add(generateOrderNo());
        });

        List<String> filterOrderNos = orderNos.stream().distinct().collect(Collectors.toList());

        System.out.println("订单样例：" + orderNos.get(22));
        System.out.println("生成订单数：" + orderNos.size());
        System.out.println("过滤重复后订单数：" + filterOrderNos.size());
        System.out.println("重复订单数：" + (orderNos.size() - filterOrderNos.size()));
    }
}
