package top.chippy.blog.interceptor;

import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.chippy.blog.config.KeyConfiguration;
import top.chippy.blog.context.BlogContext;
import top.chippy.blog.exception.UserTokenIsEmptyException;
import top.chippy.blog.exception.UserTokenNotEmptyException;
import top.chippy.blog.jwt.JwtInfo;
import top.chippy.blog.jwt.JwtTokenUtil;
import top.chippy.blog.service.AccessLimitService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Description check user token
 * @Author chippy
 */
@Slf4j
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private AccessLimitService accessLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("check user token...");

        if (!accessLimitService.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            throw new UserTokenIsEmptyException("服务端令牌为空");
        }

        String token = request.getHeader(keyConfiguration.getTokenHeader());
        if (Stringer.isNullOrEmpty(token)) {
            throw new UserTokenNotEmptyException("用户令牌不能为空");
        }

        JwtInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);

        BlogContext.setEmail(infoFromToken.getEmail());
        BlogContext.setName(infoFromToken.getName());
        BlogContext.setUserID(infoFromToken.getUserId());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        BlogContext.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
