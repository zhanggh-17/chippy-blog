package top.chippy.blog;

import com.ace.cache.EnableAceCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Date: 2019/1/4 13:57
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@SpringBootApplication
@EnableAceCache
@MapperScan("top.chippy.blog.mapper")
@EnableScheduling
@EnableTransactionManagement
public class BlogServer {

    public static void main(String[] args) {
        SpringApplication.run(BlogServer.class, args);
    }
}
