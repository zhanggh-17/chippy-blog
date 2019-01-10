package top.chippy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.chippy.blog.interceptor.CrossDomainInterceptor;
import top.chippy.blog.interceptor.UserAuthRestInterceptor;

/**
 * @Date: 2019/1/4 16:07
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossDomainInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(userAuthRestInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/user/**");
    }

    @Bean
    public CrossDomainInterceptor crossDomainInterceptor() {
        return new CrossDomainInterceptor();
    }

    @Bean
    public UserAuthRestInterceptor userAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }
}
