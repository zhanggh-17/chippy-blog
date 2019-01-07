package top.chippy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.chippy.blog.interceptor.CrossDomainInterceptor;

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
    }

    @Bean
    public CrossDomainInterceptor crossDomainInterceptor() {
        return new CrossDomainInterceptor();
    }
}
