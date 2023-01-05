package com.xiao.exception;

import com.xiao.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiao
 */
@Slf4j
@ControllerAdvice("com.xiao")
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("GlobalExceptionHandler", e);
        return Result.fail(-1, e.getMessage());
    }
}
