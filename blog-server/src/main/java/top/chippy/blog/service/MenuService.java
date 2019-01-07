package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Menu;
import top.chippy.blog.mapper.MenuMapper;

/**
 * @Date: 2019/1/4 14:34
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class MenuService extends BaseMysqlService<MenuMapper, Menu> {
}
