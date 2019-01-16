package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chippy.blog.entity.Dictionary;
import top.chippy.blog.service.DictionaryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/1/16 16:21
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/nodes/{parentId}")
    public Object nodes(@PathVariable("parentId") String parentId) {
        if (Stringer.isNullOrEmpty(parentId)) {
            return error("父节点id不能为空");
        }
        List<Dictionary> dictionaryList = dictionaryService.selectByCondition(parentId);
        return success(dictionaryList);
    }
}
