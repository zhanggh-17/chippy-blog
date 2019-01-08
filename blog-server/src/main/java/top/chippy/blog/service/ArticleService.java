package top.chippy.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.util.Stringer;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Article;
import top.chippy.blog.mapper.ArticleMapper;

import java.util.List;

/**
 * @Date: 2019/1/7 9:36
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class ArticleService extends BaseMysqlService<ArticleMapper, Article> {

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

    public List<Article> news() {
        return mapper.news(BlogConstant.DEFAULT_NEWS_LIMIT);
    }

    public List<Article> hots() {
        return mapper.hots(BlogConstant.DEFAULT_HOTS_LIMIT);
    }

    public List<Article> relation(String type) {
        List<Article> relationList = mapper.relation(type, BlogConstant.DEFAULT_RELATION_LIMIT);
        return relationList;
    }

    public Article single(String id) {
        return mapper.single(id);
    }
}
