package org.karl.sh.auth.controller;

import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.core.templates.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KARL ROSE
 * @date 2020/9/17 18:51
 **/
@ResponseBody
@ControllerAdvice
public class ExceptionGlobalHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionGlobalHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ApiResult.error("illegal Argument");
    }

    @ExceptionHandler(BaseException.class)
    public ApiResult<String> baseExceptionHandler(BaseException e) {
        log.error(e.getMessage());
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return ApiResult.error("something is wrong");
    }
}
