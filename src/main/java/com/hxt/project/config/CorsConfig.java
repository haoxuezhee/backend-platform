package com.hxt.project.config;

import com.hxt.project.Interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: CorsConfig
 * Package: com.hxt.project.config
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/30 16:51
 * @Version 1.0
 */
@Configuration
public class CorsConfig {
    @Autowired
    private JWTInterceptor jwtInterceptor;
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 允许跨域访问的路径
                        .allowedOrigins("http://localhost:9999")  // 允许跨域访问的源
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
                        .allowedHeaders("token", "Content-Type", "Authorization")  // 明确允许 token 头
                        .allowCredentials(true);  // 允许携带凭证
            }
        };
    }
}
