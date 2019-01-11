package top.chippy.blog.task;

import com.ace.cache.api.CacheAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Date: 2019/1/11 10:12
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Component
public class ArticleTask {

    @Autowired
    private CacheAPI cacheAPI;

    @Scheduled(cron = "0 1 0 * * ?") // 每天执行一次
    public void updateArticleReading() {
        // 刷新redis中文章阅读量的缓存
        cacheAPI.removeByPre("article:single");
    }
}
