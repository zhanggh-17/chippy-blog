package top.chippy.blog.constant;

/**
 * @Date: 2019/1/7 11:03
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
public class BlogConstant {

    // 分页默认第一页
    public static final int DEFAULT_PAGE_NUM = 1;
    // 搜索默认分页数量
    public static int DEFAULT_SEARCH_LIMIT = 4;
    // 最新文章默认分页数量
    public static int DEFAULT_NEWS_LIMIT = 6;
    // 精品文章默认分页数量
    public static int DEFAULT_HOTS_LIMIT = 6;
    // 相关文章默认分页数量
    public static int DEFAULT_RELATION_LIMIT = 6;
    // 没有上下文章提示
    public static final String MESSAGE_TIP = "已经没有文章了";

    // ==============redis==============
    // 字典前缀
    public static final String DICTIONARY_PRE = "dictionary";
    // 文章前缀
    public static final String ARTICLE_SINGLE_PRE = "article:single";
    // 相关文章
    public static final String ARTICLE_RELATION = "article:relations";
    // 热门文章
    public static final String ARTICLE_HOTS = "article:hots";
    // 最新文章
    public static final String ARTICLE_NEWS = "article:news";

    // 菜单下来框
    public static final String SELECT_MENU = "menu:select";
}
