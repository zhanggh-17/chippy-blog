package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Menu;

import java.util.List;

/**
 * @program: chippy-blog
 * @Date: 2019/1/4 14:28
 * @Author: chippy
 * @Description:
 */
public interface MenuMapper extends Mapper<Menu> {
    @Select("SELECT id, title FROM chippy_menu WHERE `type` = 1")
    List<Menu> laodSelectMenus();
}
