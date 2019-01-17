package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.ace.cache.api.CacheAPI;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.constant.ProjectConstant;
import com.loser.common.util.EntityUtils;
import com.loser.common.util.Stringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Article;
import top.chippy.blog.mapper.ArticleMapper;
import top.chippy.blog.vo.Params;

import java.util.List;

/**
 * @Date: 2019/1/7 9:36
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class ArticleService extends BaseMysqlService<ArticleMapper, Article> {

    @Autowired
    private CacheAPI cacheAPI;

    public PageInfo<Article> list(String search, Integer limit, Integer pageNum) {
        if (Stringer.isNullOrEmpty(limit))
            limit = BlogConstant.DEFAULT_SEARCH_LIMIT;
        if (Stringer.isNullOrEmpty(pageNum))
            pageNum = BlogConstant.DEFAULT_PAGE_NUM;
        PageHelper.startPage(pageNum, limit);
        List<Article> articleList = mapper.list(search);
        PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
        return pageInfo;
    }

    @Cache(key = BlogConstant.ARTICLE_NEWS)
    public List<Article> news() {
        return mapper.news(BlogConstant.DEFAULT_NEWS_LIMIT);
    }

    @Cache(key = BlogConstant.ARTICLE_HOTS)
    public List<Article> hots() {
        return mapper.hots(BlogConstant.DEFAULT_HOTS_LIMIT);
    }

    @Cache(key = BlogConstant.ARTICLE_RELATION)
    public List<Article> relation(String type) {
        List<Article> relationList = mapper.relation(type, BlogConstant.DEFAULT_RELATION_LIMIT);
        return relationList;
    }

    @Cache(key = BlogConstant.ARTICLE_SINGLE_PRE + "{1}")
    public Article single(String id) {
        return mapper.single(id);
    }

    // @CacheClear(keys = {"article:news", "article:hots", "article:relations"})
    public int save(Article article) {
        cacheAPI.remove("article:news", "article:hots", "article:relations");
        EntityUtils.setCreatAndUpdatInfo(article);
        return mapper.insertSelective(article);
    }

    /**
     * @Description 更新阅读量
     * @Author chippy
     * @Datetime 2019/1/11 10:28
     */
    public void updateArticleReading(String id) {
        mapper.updateArticleReading(id);
    }

    public Article preArticle(String type, int articleNo) {
        return mapper.preArticle(type, articleNo);
    }

    public Article posArticle(String type, int articleNo) {
        return mapper.posArticle(type, articleNo);
    }

    public synchronized int selectMaxArticleNo(String type) {
        return mapper.selectMaxArticleNo(type);
    }


    public PageInfo<Article> all(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<Article> list = mapper.all(params);
        PageInfo<Article> pageinfo = new PageInfo<Article>(list);
        return pageinfo;
    }

    /**
     * @Description 更改文章的状态
     * @Author chippy
     * @Datetime 2019/1/17 17:57
     */
    @CacheClear(keys = {BlogConstant.ARTICLE_SINGLE_PRE + "{1}",
                        BlogConstant.ARTICLE_NEWS,
                        BlogConstant.ARTICLE_HOTS,
                        BlogConstant.ARTICLE_RELATION})
    public int state(String id) {
        int state = mapper.selectArticleState(id);
        Article article = new Article();
        if (state == 1) {
            article.setDelFlag(2);
        } else {
            article.setDelFlag(1);
        }
        article.setId(id);
        return mapper.updateByPrimaryKeySelective(article);
    }

    public int update(Article article) {
        article.setDelFlag(ProjectConstant.SYSTEM_ENABLE); // 标识是修改
        EntityUtils.setUpdatedInfo(article);
        return mapper.updateByPrimaryKeySelective(article);
    }
}
