package top.chippy.blog.service;

import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.util.Stringer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.chippy.blog.constant.CommonConstants;
import top.chippy.blog.entity.User;
import top.chippy.blog.exception.UserNameNotExistsException;
import top.chippy.blog.exception.UserPasswordErrorException;
import top.chippy.blog.jwt.JwtInfo;
import top.chippy.blog.jwt.JwtTokenUtil;
import top.chippy.blog.mapper.UserMapper;

/**
 * @Date: 2019/1/9
 * @Author: chippy
 * @Description:
 */
@Service
public class UserService extends BaseMysqlService<UserMapper, User> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String login(User user) throws Exception {
        User userinfo = validate(user);
        return jwtTokenUtil.generateToken(new JwtInfo(userinfo.getEmail(), userinfo.getId(), userinfo.getName()));
    }

    private User validate(User user) throws UserNameNotExistsException, UserPasswordErrorException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(CommonConstants.PASSWORD_SALT);
        User copyuser = new User();

        User userinfo = mapper.validate(user.getEmail());
        if (Stringer.isNullOrEmpty(userinfo))
            throw new UserNameNotExistsException("用户名不存在");
        if (!encoder.matches(user.getPassword(), userinfo.getPassword())) {
            throw new UserPasswordErrorException("密码错误");
        } else {
            BeanUtils.copyProperties(userinfo, copyuser);
        }
        return copyuser;
    }

    public int exists(String email) {
        return mapper.exists(email);
    }
}