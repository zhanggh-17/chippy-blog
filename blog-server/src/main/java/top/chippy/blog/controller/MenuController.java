package top.chippy.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.util.BeanPropertiesUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.entity.Menu;
import top.chippy.blog.service.MenuService;
import top.chippy.blog.vo.MenuTree;
import top.chippy.blog.vo.Params;

import java.util.List;

/**
 * @Date: 2019/1/4 15:26
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @IgnoreAuth
    @GetMapping("/list")
    public Object list() {
        List<Menu> menus = menuService.list();
        return success(menus);
    }

    @IgnoreAuth
    @GetMapping("/manager")
    public Object managerMenus() {
        List<MenuTree> list = menuService.managerMenus();
        return success(list);
    }

    @GetMapping("/all/list")
    public Object allMenus(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Stringer.isNullOrEmpty(params.getSearch())) {
            criteria.andLike("title", "%" + params.getSearch() + "%");
        }
        List<Menu> list = menuService.selectByExample(example);
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(list);
        return success(pageInfo);
    }

    @PostMapping("/save")
    public Object save(Menu menu) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(menu, new String[]{"title", "type", "href", "parentId"});
        int flag = 0;
        try {
            flag = menuService.save(menu);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("添加错误的菜单信息 >>> " + menu, e);
        }
        return success(flag);
    }

    @PostMapping("/update")
    public Object update(Menu menu) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(menu, new String[]{"title", "type", "href", "parentId"});
        int flag = 0;
        try {
            flag = menuService.update(menu);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("修改错误的菜单信息 >>> " + menu, e);
        }
        return success(flag);
    }

    @GetMapping("/select/menus")
    public Object loadSelectMenus() {
        List<Menu> menus = menuService.laodSelectMenus();
        return success(menus);
    }

    @GetMapping("/{id}")
    public Object menu(@PathVariable("id") String id) {
        Menu menu = menuService.selectById(id);
        return success(menu);
    }

    @DeleteMapping("/{id}")
    public Object remove(@PathVariable("id") String id) {
        int flag = 0;
        if (Stringer.isNullOrEmpty(id)) {
            return error("请选择要删除的菜单");
        }
        try {
            flag = menuService.remove(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("删除菜单出错的id >>> " + id, e);
        }
        return success(flag);
    }
}