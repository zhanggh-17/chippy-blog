package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import org.springframework.stereotype.Service;
import top.chippy.blog.entity.UserRole;
import top.chippy.blog.mapper.UserRoleMapper;

/**
 * @Date: 2019/1/18 11:38
 * @program: chippy-blog
 * @Author: chippy
 * @Description:
 */
@Service
public class UserRoleService extends BaseMysqlService<UserRoleMapper, UserRole> {
}
