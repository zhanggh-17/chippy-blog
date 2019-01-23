package top.chippy.blog.controller;

import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.util.BeanPropertiesUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Article;
import top.chippy.blog.service.ArticleService;
import top.chippy.blog.vo.ArticleParams;
import top.chippy.blog.vo.Params;

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
    @IgnoreAuth
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
    @IgnoreAuth
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
    @IgnoreAuth
    @GetMapping("/hots")
    public Object hots() {
        List<Article> list = articleService.hots();
        return success(list);
    }

    @IgnoreAuth
    @GetMapping("/single/{id}")
    public Object single(@PathVariable("id") String id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String type = null; // 类型
        List<Article> relationList = null; // 相关文章
        Article article = articleService.single(id);
        if (!Stringer.isNullOrEmpty(article)) {
            type = article.getType();
            relationList = articleService.relation(type);
            // 上一篇
            Article preArtcile = articleService.preArticle(type, article.getArticleNo());
            // 下一篇
            Article posArticle = articleService.posArticle(type, article.getArticleNo());
            if (Stringer.isNullOrEmpty(preArtcile)) {
                preArtcile = new Article();
                preArtcile.setTitle(BlogConstant.MESSAGE_TIP);
            }
            if (Stringer.isNullOrEmpty(posArticle)) {
                posArticle = new Article();
                posArticle.setTitle(BlogConstant.MESSAGE_TIP);
            }
            resultMap.put("pre", preArtcile);
            resultMap.put("pos", posArticle);
            resultMap.put("relation", relationList);
        }

        resultMap.put("single", article);

        // 更新阅读量
        articleService.updateArticleReading(id);
        return success(resultMap);
    }

    @PostMapping("/save")
    public Object save(Article article) throws IllegalAccessException {
        String[] fields = new String[]{"title", "content", "halfContent", "cover", "author", "type"};
        BeanPropertiesUtil.fieldsNotNullOrEmpty(article, fields);

        int flag = 0;
        try {
            Integer maxArticleNo = articleService.selectMaxArticleNo(article.getType());
            if (Stringer.isNullOrEmpty(maxArticleNo)) {
                maxArticleNo = 0;
            }
            article.setArticleNo(maxArticleNo + 1);
            flag = articleService.save(article);
        } catch (Exception e) {
            log.error("添加文章失败 >>> " + article, e);
            log.error(e.getMessage());
        }
        return success(flag);
    }

    @PostMapping("/update")
    public Object update(Article article) throws IllegalAccessException {
        if (Stringer.isNullOrEmpty(article.getId())) {
            return error("请选择需要修改的文章");
        }
        String[] fields = new String[]{"title", "content", "halfContent", "cover", "author", "type"};
        BeanPropertiesUtil.fieldsNotNullOrEmpty(article, fields);

        int flag = 0;
        try {
            flag = articleService.update(article);
        } catch (Exception e) {
            log.error("修改文章失败 >>> " + article, e);
            log.error(e.getMessage());
        }
        return success(flag);
    }

    @GetMapping("/all/list")
    public Object all(ArticleParams articleParams){
        PageInfo<Article> pageInfo = articleService.all(articleParams);
        return success(pageInfo);
    }

    @PostMapping("/state")
    public Object state(String id) {
        int flag = 0;
        if (Stringer.isNullOrEmpty(id)) {
            return error("请选择要更新状态的文章");
        }
        try {
            flag = articleService.state(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("更新文章状态出错的id >>> " + id, e);
        }
        return success(flag);
    }

    @GetMapping("/{id}")
    public Object article(@PathVariable("id") String id) {
        Article article = articleService.selectById(id);
        return success(article);
    }
}
