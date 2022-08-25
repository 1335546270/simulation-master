package com.iqilu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截方法
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }

    /**
     * 解决拦截器无法注入问题
     *
     * @author zhangyicheng
     * @date 2020/05/21
     */
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
