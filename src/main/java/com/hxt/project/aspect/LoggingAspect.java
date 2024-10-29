package com.hxt.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * ClassName: LoggingAspect
 * Package: com.hxt.project.aspect
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/22 20:25
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {


    // 环绕通知，拦截所有controller的save、update、delete方法
    @Around("execution(* com.hxt.project.controller.*.*(..)) && " +
            "(execution(* save*(..)) || execution(* update*(..)) || execution(* delete*(..)))")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        log.info("方法名 [{}] : {}", methodName, Arrays.toString(args));

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 记录方法执行时间
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("方法 [{}] 执行用时 {} ms", methodName, timeTaken);

        return result;
    }

    // 用于记录请求的详细信息，包括地址、类型、参数等
    @Around("execution(* com.hxt.project.controller.*.*(..))")
    public Object logRequestInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request != null) {
            log.info("请求 URL: [{}] | 方法: [{}] | 参数: [{}]",
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    Arrays.toString(joinPoint.getArgs()));
        }

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("请求执行用时 {} ms", timeTaken);

        return result;
    }
}
