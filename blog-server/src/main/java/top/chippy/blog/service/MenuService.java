package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.constant.ProjectConstant;
import com.loser.common.util.EntityUtils;
import com.loser.common.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.entity.Menu;
import top.chippy.blog.mapper.MenuMapper;
import top.chippy.blog.vo.MenuTree;

import java.util.ArrayList;
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
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", -1);
        example.orderBy("sort").asc();
        List<Menu> menus = mapper.selectByExample(example);
        return menus;
    }

    public List<MenuTree> managerMenus() {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        example.orderBy("sort").asc();
        List<Menu> list = mapper.selectByExample(example);
        List<MenuTree> menuTree = getMenuTree(list, ProjectConstant.MENU_ROOT);
        return menuTree;
    }

    private List<MenuTree> getMenuTree(List<Menu> menus, String root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }

    @Cache(key = BlogConstant.SELECT_MENU)
    public List<Menu> laodSelectMenus() {
        return mapper.laodSelectMenus();
    }

    @CacheClear(key = BlogConstant.SELECT_MENU)
    public int save(Menu menu) {
        EntityUtils.setCreatAndUpdatInfo(menu);
        return mapper.insertSelective(menu);
    }

    @CacheClear(key = BlogConstant.SELECT_MENU)
    public int update(Menu menu) {
        EntityUtils.setUpdatedInfo(menu);
        return mapper.updateByPrimaryKeySelective(menu);
    }
}
