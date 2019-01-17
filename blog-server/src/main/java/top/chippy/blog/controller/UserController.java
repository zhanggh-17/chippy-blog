package top.chippy.blog.controller;

import com.github.pagehelper.PageInfo;
import com.loser.common.base.controller.BaseController;
import com.loser.common.constant.ProjectConstant;
import com.loser.common.util.BeanPropertiesUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.constant.CommonConstants;
import top.chippy.blog.entity.User;
import top.chippy.blog.exception.UserNameNotExistsException;
import top.chippy.blog.exception.UserPasswordErrorException;
import top.chippy.blog.jwt.JwtInfo;
import top.chippy.blog.jwt.JwtTokenUtil;
import top.chippy.blog.service.UserService;
import top.chippy.blog.vo.Params;

/**
 * @Date: 2019/1/9
 * @program: xx
 * @Author: chippy
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /*
     * @Description 注册
     * @author chippy
     * @date 2019/1/9 21:44
     * @return
     */
    @IgnoreAuth
    @PostMapping("/register")
    public Object register(User user) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(user, new String[]{"name", "email", "password"});
        log.debug(user.getEmail() + " raw password >>> " + user.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(CommonConstants.PASSWORD_SALT);
        String encoderPassword = encoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        int flag = 0;
        try {
            flag = userService.insertSelective(user);
        } catch (Exception e) {
            log.error("注册失败 >>> " + user, e);
            log.error(e.getMessage());
        }
        return success(flag);
    }

    /*
     * @Description 登录
     * @author chippy
     * @date 2019/1/9 21:44
     * @return
     */
    @IgnoreAuth
    @PostMapping(value = "/login")
    public Object login(User user) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(user, new String[]{"email", "password"});
        String token = null;
        try {
            token = userService.login(user);
        } catch (UserNameNotExistsException userNameNotExistsException) {
            log.error(userNameNotExistsException.getMessage(), userNameNotExistsException, "user >>> " + user);
            return error(userNameNotExistsException.getMessage());
        } catch (UserPasswordErrorException userPasswordErrorException) {
            log.error(userPasswordErrorException.getMessage(), userPasswordErrorException, "user >>> " + user);
            return error(userPasswordErrorException.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e, "user >>> " + user);
            return error(CommonConstants.SERVER_EXCEPTION);
        }
        return success(token);
    }

    @IgnoreAuth
    @GetMapping("/exists/{email}")
    public Object exists(@PathVariable("email") String email) {
        int flag = userService.exists(email);
        return success(flag);
    }

    @GetMapping("/userinfo")
    public Object userInfo(String token) {
        JwtInfo jwtInfo = null;
        User user = null;
        try {
            jwtInfo = jwtTokenUtil.getInfoFromToken(token);
            user = userService.userInfo(jwtInfo.getUserId());
            return success(user);
        } catch (Exception e) {
            log.info(e.getMessage());
            return error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Object list(Params params) {
        PageInfo<User> pageInfo = userService.list(params);
        return success(pageInfo);
    }

    @PostMapping("/save")
    public Object save(User user) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(user, new String[]{"name", "email"});
        int flag = 0;
        try {
            flag = userService.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("添加错误的用户信息 >>> " + user, e);
        }
        return success(flag);
    }

    @PostMapping("/update")
    public Object update(User user) throws IllegalAccessException {
        if (Stringer.isNullOrEmpty(user.getId())) {
            return error("请选择您要修改的用户");
        }
        BeanPropertiesUtil.fieldsNotNullOrEmpty(user, new String[]{"name", "email"});
        int flag = 0;
        try {
            flag = userService.update(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("修改错误的用户信息 >>> " + user, e);
        }
        return success(flag);
    }

    @PostMapping("/state")
    public Object state(String id) {
        int flag = 0;
        if (Stringer.isNullOrEmpty(id)) {
            return error("请选择要更新状态的用户");
        }
        try {
            flag = userService.state(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("更新用户状态出错的id >>> " + id, e);
        }
        return success(flag);
    }

    @GetMapping("/{id}")
    public Object user(@PathVariable("id") String id) {
        User user = new User();
        user.setId(id);
        user.setDelFlag(ProjectConstant.SYSTEM_ENABLE);
        User userinfo = userService.selectOne(user);
        userinfo.setPassword(null);
        return success(userinfo);
    }
}