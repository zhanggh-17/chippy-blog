package com.loser.common.hanlder;

import com.loser.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.loser.common.base.entity.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by K2 on 2017/9/8.
 * <p>
 * 在各自的工程中 继承该异常类，添加相关注解，捕获相关异常
 */
//@ControllerAdvice("xx.xx.xx")
//@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus());
        return new ResponseEntity(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity(500, ex.getMessage());
    }

}
