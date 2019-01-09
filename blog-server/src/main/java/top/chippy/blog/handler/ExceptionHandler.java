package top.chippy.blog.handler;

import com.loser.common.hanlder.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : Storydo
 * @Title: ExceptionHandler.java
 * @Description: TODO
 * @date: 2018年9月18日 下午7:29:46
 * @version:V1.0 Copyright 悦享互联 2016 All right reserved.
 * Modification  History:
 * Version       Date          Author          Description
 * ----------------------------------------------------------------------------
 * 1.0         2018年9月18日        Storydo              TODO
 */

@RestControllerAdvice("top.chippy.blog.controller")
public class ExceptionHandler extends GlobalExceptionHandler {
}