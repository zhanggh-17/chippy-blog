package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chippy.blog.entity.Tag;
import top.chippy.blog.service.TagService;

import java.util.List;

/**
 * @Date: 2019/1/7 15:27
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
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
    @GetMapping("/list")
    public Object list() {
        List<Tag> tagList = tagService.selectListAll();
        return success(tagList);
    }

}
