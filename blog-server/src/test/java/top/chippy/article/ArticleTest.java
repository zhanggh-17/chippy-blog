package top.chippy.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.chippy.blog.BlogServer;
import top.chippy.blog.entity.Article;
import top.chippy.blog.service.ArticleService;

import java.util.List;

/**
 * @Date: 2019/1/7 15:49
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogServer.class)
@AutoConfigureMockMvc
public class ArticleTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void test() {
        List<Article> relation = articleService.relation("1001");
        for (Article article : relation) {
            System.out.println(article);
        }
    }
}
