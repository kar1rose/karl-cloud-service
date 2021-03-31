package org.karl.sh.warehouse.controller;

import lombok.extern.slf4j.Slf4j;
import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.core.templates.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KARL ROSE
 * @date 2020/9/17 18:51
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ApiResult<String> baseExceptionHandler(BaseException e) {
        return ApiResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception e) {
        return ApiResult.error(e.getMessage());
    }
}
