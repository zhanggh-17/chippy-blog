package top.chippy.blog.handler;

import com.loser.common.hanlder.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * @Description 切面控制异常
 * @author chippy
 * @date 2019/1/9 22:01
 */
@RestControllerAdvice("top.chippy.blog.controller")
public class ExceptionHandler extends GlobalExceptionHandler {
}