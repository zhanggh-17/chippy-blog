package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Tag;
import top.chippy.blog.mapper.TagMapper;

/**
 * @Date: 2019/1/7 15:29
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class TagService extends BaseMysqlService<TagMapper, Tag> {
}
