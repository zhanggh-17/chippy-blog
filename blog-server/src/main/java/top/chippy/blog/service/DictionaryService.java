package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.constant.ProjectConstant;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Dictionary;
import top.chippy.blog.mapper.DictionaryMapper;

import java.util.List;

/**
 * @Date: 2019/1/16 16:21
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class DictionaryService extends BaseMysqlService<DictionaryMapper, Dictionary> {

    /**
     * @Description 获取某个父节点id下的子节点数据
     * @Author chippy
     * @Datetime 2019/1/16 16:28
     */
//    @Cache(key = BlogConstant.DICTIONARY_PRE + "{1}")
//    public List<Dictionary> selectByCondition(String parentId) {
//        Example example = new Example(Dictionary.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentId", parentId);
//        criteria.andEqualTo("isPost", ProjectConstant.SYSTEM_ENABLE);
//        example.orderBy("sort").asc();
//        example.orderBy("dicCode").desc();
//        return mapper.selectByExample(example);
//    }

    @Cache(key = BlogConstant.DICTIONARY_PRE + "{1}")
    public List<Dictionary> selectByCondition(String parentId) {
        return mapper.selectByCondition(parentId);
    }
}
