package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.ace.cache.api.CacheAPI;
import com.alibaba.fastjson.JSONObject;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.constant.ProjectConstant;
import com.loser.common.util.EntityUtils;
import com.loser.common.util.Stringer;
import com.loser.common.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CacheAPI cacheAPI;

    @Cache(key = BlogConstant.BEFORE_MENU)
    public List<Menu> list() {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", -1);
        example.orderBy("sort").asc();
        List<Menu> menus = mapper.selectByExample(example);
        return menus;
    }

    public List<MenuTree> managerMenus() {
        List<MenuTree> menuTrees = null;
        List<Menu> list = null;
        String resultJson = cacheAPI.get(BlogConstant.AFTER_MENU);
        if (Stringer.isNullOrEmpty(resultJson)) {
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("type", 1);
            example.orderBy("sort").asc();
            list = mapper.selectByExample(example);
            cacheAPI.set(BlogConstant.AFTER_MENU, list, 1440);
        } else {
            list = JSONObject.parseArray(resultJson, Menu.class);
        }
        menuTrees = getMenuTree(list, ProjectConstant.MENU_ROOT);
        return menuTrees;
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

    @CacheClear(keys = {BlogConstant.SELECT_MENU, BlogConstant.BEFORE_MENU, BlogConstant.AFTER_MENU})
    public int save(Menu menu) {
        EntityUtils.setCreatAndUpdatInfo(menu);
        return mapper.insertSelective(menu);
    }

    @CacheClear(keys = {BlogConstant.SELECT_MENU, BlogConstant.BEFORE_MENU, BlogConstant.AFTER_MENU})
    public int update(Menu menu) {
        EntityUtils.setUpdatedInfo(menu);
        return mapper.updateByPrimaryKeySelective(menu);
    }

    @CacheClear(keys = {BlogConstant.SELECT_MENU, BlogConstant.BEFORE_MENU, BlogConstant.AFTER_MENU})
    public int remove(String id) {
        return mapper.deleteByPrimaryKey(id);
    }
}
