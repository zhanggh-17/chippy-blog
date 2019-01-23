package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Contact;

import java.util.List;

/**
 * @program: chippy-blog
 * @Date: 2019/1/4 14:27
 * @Author: chippy
 * @Description:
 */
public interface ContactMapper extends Mapper<Contact> {

    List<Contact> list(@Param("search") String search);
}
