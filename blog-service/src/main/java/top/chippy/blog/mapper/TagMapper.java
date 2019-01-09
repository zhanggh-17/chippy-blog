package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Tag;

import java.util.List;

/**
 * @program: chippy-blog
 * @Date: 2019/1/4 14:30
 * @Author: chippy
 * @Description:
 */
public interface TagMapper extends Mapper<Tag> {
    List<Tag> list();

    @Select("SELECT id, name FROM chippy_tag")
    Tag single(String id);
}
