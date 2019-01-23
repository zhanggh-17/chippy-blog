package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chippy.blog.entity.Role;
import top.chippy.blog.service.RoleService;

import java.util.List;

/**
 * @Date: 2019/1/18 10:44
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/select")
    public Object select() {
        List<Role> list = roleService.select();
        return success(list);
    }
}
