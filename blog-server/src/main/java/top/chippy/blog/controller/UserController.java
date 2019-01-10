package top.chippy.blog.controller;

import com.loser.common.base.controller.BaseController;
import com.loser.common.util.BeanPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.chippy.blog.constant.CommonConstants;
import top.chippy.blog.entity.User;
import top.chippy.blog.exception.UserNameNotExistsException;
import top.chippy.blog.exception.UserPasswordErrorException;
import top.chippy.blog.service.UserService;

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

    /*
     * @Description 注册
     * @author chippy
     * @date 2019/1/9 21:44
     * @return
     */
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
            log.error("注册失败", e);
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
    @PostMapping("/login")
    public Object login(User user) throws IllegalAccessException {
        BeanPropertiesUtil.fieldsNotNullOrEmpty(user, new String[]{"email", "password"});
        String token = null;
        try {
            token = userService.login(user);
        } catch (UserNameNotExistsException userNameNotExistsException) {
            log.error(userNameNotExistsException.getMessage(), userNameNotExistsException);
            return error(userNameNotExistsException.getMessage());
        } catch (UserPasswordErrorException userPasswordErrorException) {
            log.error(userPasswordErrorException.getMessage(), userPasswordErrorException);
            return error(userPasswordErrorException.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return error(CommonConstants.SERVER_EXCEPTION);
        }
        return success(token);
    }

    @GetMapping("/exists/{email}")
    public Object exists(@PathVariable("email") String email) {
        int flag = userService.exists(email);
        return success(flag);
    }
}