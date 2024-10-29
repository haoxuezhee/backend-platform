package com.hxt.project.config;

import com.hxt.project.Interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: InterceptorConfig
 * Package: com.hxt.project.config
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/31 20:58
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(redisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/list","/dept/list",
                                        "/goods/list","/menu/list","/menu/nodes","/menu//{id}",
                                        "/permission/list","/role/list","/var/list","/product/list","/product/uploadExcel"
                                            ,"/images/**", "/static/**");
    }
}
