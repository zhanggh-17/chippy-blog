package top.chippy.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.entity.Tag;
import top.chippy.blog.service.TagService;
import top.chippy.blog.vo.Params;

import java.util.List;

/**
 * @Date: 2019/1/7 15:27
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    /**
     * @Description 查询所有的tag标签
     * @Author chippy
     * @Datetime 2019/1/7 15:29
     */
    @IgnoreAuth
    @GetMapping("/list")
    public Object list() {
        List<Tag> tagList = tagService.list();
        return success(tagList);
    }

    /**
     * @Description 获取某个信息
     * @Author chippy
     * @Datetime 2019/1/9 16:01
     */
    @GetMapping("/{id}")
    public Object single(@PathVariable("id") String id) {
        Tag tag = tagService.single(id);
        return success(tag);
    }

    @GetMapping("/all/list")
    public Object all(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        Example example = new Example(Tag.class);
        Example.Criteria criteria = example.createCriteria();
        if (!Stringer.isNullOrEmpty(params.getSearch())) {
            criteria.andLike("name", "%" + params.getSearch() + "%");
        }
        List<Tag> list = tagService.selectByExample(example);
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(list);
        return success(pageInfo);
    }

    @PostMapping("/save")
    public Object save(Tag tag) {
        if (Stringer.isNullOrEmpty(tag.getName())) {
            return error("名称不能为空");
        }
        int flag = 0;
        try {
            flag = tagService.save(tag);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("添加错误的标签信息 >>> " + tag, e);
        }
        return success(flag);
    }

    @PostMapping("/update")
    public Object update(Tag tag) {
        if (Stringer.isNullOrEmpty(tag.getName())) {
            return error("名称不能为空");
        }
        int flag = 0;
        try {
            flag = tagService.update(tag);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("修改错误的标签信息 >>> " + tag, e);
        }
        return success(flag);
    }

    @DeleteMapping("/{id}")
    public Object remove(@PathVariable("id") String id) {
        int flag = 0;
        if (Stringer.isNullOrEmpty(id)) {
            return error("请选择要删除的标签");
        }
        try {
            flag = tagService.remove(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("删除标签出错的id >>> " + id, e);
        }
        return success(flag);
    }

}
