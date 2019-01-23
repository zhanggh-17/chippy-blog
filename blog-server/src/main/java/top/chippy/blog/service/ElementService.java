package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Element;
import top.chippy.blog.mapper.ElementMapper;

/**
 * @Date: 2019/1/18 16:09
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class ElementService extends BaseMysqlService<ElementMapper, Element> {
}
