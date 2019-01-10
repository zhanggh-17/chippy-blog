package top.chippy.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Date: 2019/1/4 16:09
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
public class CrossDomainInterceptor implements HandlerInterceptor {

    @Value("${spring.profiles.active}")
    String env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("CrossDomainInterceptor is starting......");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers",  "Content-Type,X-Requested-With,Authorization");
        response.setHeader("Access-Control-Max-Age", "86400");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

