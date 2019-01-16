package top.chippy.blog.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import top.chippy.blog.entity.Dictionary;

import java.util.List;

/**
 * @program: chippy-blog
 * @Date: 2019/1/4 14:27
 * @Author: chippy
 * @Description:
 */
public interface DictionaryMapper extends Mapper<Dictionary> {

    @Select("SELECT id,dic_code AS dicCode,item_name AS itemName " +
            "FROM base_dictionary " +
            "WHERE (del_flag = 1 AND parent_id = #{parentId} AND is_post = 1 ) " +
            "ORDER BY sort ASC,dic_code DESC")
    List<Dictionary> selectByCondition(@Param("parentId") String parentId);

}
