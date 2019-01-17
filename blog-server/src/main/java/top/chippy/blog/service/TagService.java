package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.util.EntityUtils;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Tag;
import top.chippy.blog.mapper.TagMapper;

import java.util.List;

/**
 * @Date: 2019/1/7 15:29
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class TagService extends BaseMysqlService<TagMapper, Tag> {

    @Cache(key = "tags")
    public List<Tag> list() {
        return mapper.list();
    }

    public Tag single(String id) {
        return mapper.single(id);
    }

    @CacheClear(key = "tags")
    public int save(Tag tag) {
        EntityUtils.setCreatAndUpdatInfo(tag);
        return mapper.insertSelective(tag);
    }

    @CacheClear(key = "tags")
    public int update(Tag tag) {
        EntityUtils.setUpdatedInfo(tag);
        return mapper.updateByPrimaryKeySelective(tag);
    }

    @CacheClear(key = "tags")
    public int remove(String id) {
        return mapper.deleteByPrimaryKey(id);
    }
}
