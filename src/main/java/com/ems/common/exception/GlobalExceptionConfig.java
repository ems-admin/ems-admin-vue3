package com.ems.common.exception;

import com.ems.common.utils.ExceptionUtil;
import com.ems.common.utils.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @program: ems-vue3
 * @description: 配置全局异常处理
 * @author: starao
 * @create: 2023-07-04 21:18
 **/
@ControllerAdvice
public class GlobalExceptionConfig extends ResultUtil {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception){
        exception.printStackTrace();
        return fail(false, ExceptionUtil.getStackTraceInfo(exception));
    }
}
