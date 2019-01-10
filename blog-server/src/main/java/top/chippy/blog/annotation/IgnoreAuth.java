package top.chippy.blog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: chippy-blog
 * @Date: 2019/1/10 10:41
 * @Author: chippy
 * @Description: 忽略验证注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreAuth {
}
