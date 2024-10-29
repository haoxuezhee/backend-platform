package com.hxt.project.exception;


import com.hxt.project.common.ErrorCode;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.hxt.usercenter.exception
 * Description:
 *          全局异常处理器
 * @Author hxt
 * @Create 2024/9/9 17:53
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Resp businessExceptionHandler(BusinessException e){
        log.error("BusinessException"+e.getMessage(),e);
        return Resp.builder().code(e.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public Resp runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException",e);
        return Resp.builder().code(RespCode.SYSTEM_ERROR).msg(e.getMessage()).build();
    }
}
