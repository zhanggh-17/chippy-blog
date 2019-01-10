package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.User;

/**
 * @Date: 2019/1/9
 * @program: xx
 * @Author: chippy
 * @Description:
 */
public interface UserMapper extends Mapper<User> {
    User validate(@Param("email") String email);

    @Select("SELECT COUNT(1) FROM chippy_user WHERE del_flag = 1 AND email = #{email}")
    int exists(@Param("email") String email);
}