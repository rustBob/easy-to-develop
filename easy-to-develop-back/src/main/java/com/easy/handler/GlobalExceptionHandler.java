package com.easy.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.easy.common.Result;
import com.easy.common.Status;
import com.easy.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 应用异常处理
     * @param e 捕获的应用异常
     * @return Result<String> 返回错误信息
     */
    @ResponseBody
    @ExceptionHandler(AppException.class)
    public Result<String> appExceptionHandler(AppException e){
        log.error(e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 登录异常处理
     * @param e 捕获的应用异常
     * @return Result<String> 返回错误信息
     */
    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    public Result<String> notLoginExceptionHandler(NotLoginException e){
        log.error(e.getMessage());
        return Result.error(Status.UNAUTHORIZED);
    }

    /**
     * 权限异常处理
     * @param e 捕获的应用异常
     * @return Result<String> 返回错误信息
     */
    @ResponseBody
    @ExceptionHandler(NotPermissionException.class)
    public Result<String> notLoginExceptionHandler(NotPermissionException e){
        log.error(e.getMessage());
        return Result.error(Status.FORBIDDEN);
    }

    /**
     * 通用异常处理
     * @param e 捕获的应用异常
     * @return Result<String> 返回错误信息
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e){
        log.error(e.getMessage());
        return Result.error(Status.ERROR);
    }
}
