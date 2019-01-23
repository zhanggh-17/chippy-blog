package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Role;

import java.util.List;

/**
 * @Date: 2019/1/18 10:46
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
public interface RoleMapper extends Mapper<Role> {
    @Select("SELECT id, name FROM base_role")
    List<Role> findSelect();
}
