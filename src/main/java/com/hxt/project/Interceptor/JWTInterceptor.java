package com.hxt.project.Interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.User;
import com.hxt.project.common.ErrorCode;
import com.hxt.project.exception.BusinessException;
import com.hxt.project.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * ClassName: JWTIntercepetor
 * Package: com.hxt.project.Interceptor
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/31 20:28
 * @Version 1.0
 */
@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public JWTInterceptor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 如果是 OPTIONS 请求，直接返回 true 以跳过拦截器的处理
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        //获取请求头中的令牌
        String token = request.getHeader("token");
        try {
            if (token == null || token.isEmpty()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"未提供Token");
            }
            System.out.println("token info："+token);
            // 验证 Token
            DecodedJWT decodedJWT = JwtUtil.verify(token);
            log.info("Token verified, DecodedJWT: {}", decodedJWT);


            Object user = redisTemplate.opsForValue().get(token);
            log.info("userInfo:{}",user);
            User userInfo = (User) user;
            if(userInfo !=null && userInfo.getStatus() ==1){
                MDC.put("userId", String.valueOf(userInfo.getId()));
                MDC.put("userName", userInfo.getUsername());
                return true;
            }else {
                throw new BusinessException(ErrorCode.NO_AUTH,"没有权限");
            }
        } catch (SignatureVerificationException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无效签名");
        } catch (TokenExpiredException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"token过期");
        } catch (AlgorithmMismatchException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"算法不一致");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

}
