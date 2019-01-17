package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.User;
import top.chippy.blog.vo.Params;

import java.util.List;

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

    @Select("SELECT id, `name`, email FROM chippy_user WHERE del_flag = 1 AND id = #{userId}")
    User userInfo(@Param("userId") String userId);

    List<User> list(@Param("params") Params params);

    @Select("SELECT del_flag AS delFlag FROM chippy_user WHERE id = #{id}")
    int selectUserState(@Param("id") String id);
}