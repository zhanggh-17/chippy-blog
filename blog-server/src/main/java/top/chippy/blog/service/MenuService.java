package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.entity.Menu;
import top.chippy.blog.mapper.MenuMapper;

import java.util.List;

/**
 * @Date: 2019/1/4 14:34
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class MenuService extends BaseMysqlService<MenuMapper, Menu> {

    @Cache(key = "menus")
    public List<Menu> list() {
        Example example = new Example(Menu.class);
        example.orderBy("sort").asc();
        List<Menu> menus = mapper.selectByExample(example);
        return menus;
    }
}
