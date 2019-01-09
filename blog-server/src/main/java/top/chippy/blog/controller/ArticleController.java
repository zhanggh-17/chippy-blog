package top.chippy.blog.controller;

import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.util.BeanPropertiesUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chippy.blog.entity.Article;
import top.chippy.blog.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/1/7 9:34
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * @Description 首页内容
     * @Author chippy
     * @Datetime 2019/1/7 14:13
     */
    @GetMapping("/list")
    public Object list(String search, Integer limit, Integer pageNum) {
        PageInfo<Article> list = articleService.list(search, limit, pageNum);
        return success(list);
    }

    /**
     * @Description 最新发布
     * @Author chippy
     * @Datetime 2019/1/7 14:13
     */
    @GetMapping("/news")
    public Object news() {
        List<Article> list = articleService.news();
        return success(list);
    }

    /**
     * @Description 精品文章
     * @Author chippy
     * @Datetime 2019/1/7 15:20
     */
    @GetMapping("/hots")
    public Object hots() {
        List<Article> list = articleService.hots();
        return success(list);
    }

    @GetMapping("/single/{id}")
    public Object single(@PathVariable("id") String id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String type = null; // 类型
        List<Article> relationList = null; // 相关文章
        Article article = articleService.single(id);
        if (!Stringer.isNullOrEmpty(article)) {
            type = article.getType();
            relationList = articleService.relation(type);
            resultMap.put("relation", relationList);
        }
        resultMap.put("single", article);
        return success(resultMap);
    }

    @PostMapping("/save")
    public Object save(Article article) throws IllegalAccessException {
        String[] fields = new String[]{"title", "content", "halfContent", "cover", "author", "type"};
        BeanPropertiesUtil.fieldsNotNullOrEmpty(article, fields);

        int flag = 0;
        try {
            flag = articleService.save(article);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return success(flag);
    }
}
