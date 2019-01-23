package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.Role;
import top.chippy.blog.mapper.RoleMapper;

import java.util.List;

/**
 * @Date: 2019/1/18 10:45
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class RoleService extends BaseMysqlService<RoleMapper, Role> {

    public List<Role> select() {
        return mapper.findSelect();
    }
}
