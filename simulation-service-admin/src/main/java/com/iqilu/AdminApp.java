package com.iqilu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class AdminApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
        log.info("project: service-admin is running !!!!!!");
    }

}
