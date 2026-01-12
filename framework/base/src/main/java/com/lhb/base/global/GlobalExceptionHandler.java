package com.lhb.base.global;

import com.lhb.base.response.ApiResponse;
import com.lhb.base.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 数据库唯一约束异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ApiResponse<?> handleSQLConstraint(SQLIntegrityConstraintViolationException e) {
        log.error("数据库异常：", e);
        return ApiResponse.fail(ResultCode.FAIL, "数据库约束异常");
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntime(RuntimeException e) {
        log.error("运行时异常：", e);
        return ApiResponse.fail(ResultCode.FAIL, e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("系统异常：", e);
        return ApiResponse.fail(ResultCode.FAIL, "系统内部错误，请联系管理员");
    }

}
