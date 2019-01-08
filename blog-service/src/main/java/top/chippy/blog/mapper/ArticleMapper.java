package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Article;

import java.util.List;

/**
 * @program: chippy-blog
 * @Date: 2019/1/4 14:22
 * @Author: chippy
 * @Description:
 */
public interface ArticleMapper extends Mapper<Article> {

    List<Article> list(@Param("search") String search);

    List<Article> news(@Param("num") int num);

    List<Article> hots(@Param("num") int num);

    List<Article> relation(@Param("type") String type, @Param("num") int num);

    Article single(@Param("id") String id);
}
