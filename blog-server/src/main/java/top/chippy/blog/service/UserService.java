package top.chippy.blog.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loser.common.base.service.BaseMysqlService;
import com.loser.common.constant.ProjectConstant;
import com.loser.common.util.EntityUtils;
import com.loser.common.util.NumUtil;
import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.chippy.blog.constant.BlogConstant;
import top.chippy.blog.constant.CommonConstants;
import top.chippy.blog.entity.User;
import top.chippy.blog.exception.UserNameNotExistsException;
import top.chippy.blog.exception.UserPasswordErrorException;
import top.chippy.blog.jwt.JwtInfo;
import top.chippy.blog.jwt.JwtTokenUtil;
import top.chippy.blog.mapper.UserMapper;
import top.chippy.blog.vo.Params;

import java.util.List;

/**
 * @Date: 2019/1/9
 * @Author: chippy
 * @Description:
 */
@Slf4j
@Service
public class UserService extends BaseMysqlService<UserMapper, User> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${spring.profiles.active}")
    String env;

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

    @Cache(key = BlogConstant.USER_INFO + "{1}")
    public User userInfo(String userId) {
        return mapper.userInfo(userId);
    }

    public int save(User user) {
        EntityUtils.setCreatAndUpdatInfo(user);
        String tempPassword;
        if (!env.equals("dis")) {
            tempPassword = "qazwsx";
        } else {
            tempPassword = NumUtil.generateTempPassword();
        }

        log.info("user >>> " + user.getEmail() + " password >>> " + tempPassword);
        String password = new BCryptPasswordEncoder(CommonConstants.PASSWORD_SALT).encode(tempPassword);

        user.setPassword(password);
        return mapper.insertSelective(user);
    }

    @CacheClear(key = BlogConstant.USER_INFO + "{1.id}")
    public int update(User user) {
        user.setDelFlag(ProjectConstant.SYSTEM_ENABLE); // 标识是修改
        EntityUtils.setUpdatedInfo(user);
        return mapper.updateByPrimaryKeySelective(user);
    }

    /**
     * @Description 更改用户的状态
     * @Author chippy
     * @Datetime 2019/1/17 16:31
     */
    @CacheClear(key = BlogConstant.USER_INFO + "{1}")
    public int state(String id) {
        int state = mapper.selectUserState(id);
        User user = new User();
        if (state == 1) {
            user.setDelFlag(2);
        } else {
            user.setDelFlag(1);
        }
        user.setId(id);
        return mapper.updateByPrimaryKeySelective(user);
    }

    public PageInfo<User> list(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<User> list = mapper.list(params);
        PageInfo<User> pageinfo = new PageInfo<User>(list);
        return pageinfo;
    }
}