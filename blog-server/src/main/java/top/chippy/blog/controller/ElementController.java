package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chippy.blog.context.BlogContext;
import top.chippy.blog.service.ElementService;

/**
 * @Date: 2019/1/18 16:09
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/element")
public class ElementController extends BaseController {

    @Autowired
    private ElementService elementService;

    @GetMapping("/user/{menuId}")
    public Object getAuthorityElement(@PathVariable("menuId") String menuId) {
        String userId = BlogContext.getUserId();
        return success("");
    }

}
