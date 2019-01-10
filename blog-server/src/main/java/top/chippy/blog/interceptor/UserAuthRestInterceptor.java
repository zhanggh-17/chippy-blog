package top.chippy.blog.interceptor;

import com.loser.common.util.Stringer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.chippy.blog.annotation.IgnoreAuth;
import top.chippy.blog.config.KeyConfiguration;
import top.chippy.blog.context.BlogContext;
import top.chippy.blog.exception.UserTokenNotEmptyException;
import top.chippy.blog.jwt.JwtInfo;
import top.chippy.blog.jwt.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    // @Autowired
    // private AccessLimitService accessLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("check user token...");

        // 先这么处理
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        //if (!accessLimitService.tryAcquire(500, TimeUnit.MILLISECONDS)) {
        //  throw new UserTokenIsEmptyException("服务端令牌为空");
        //}

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 先从类上拿
        IgnoreAuth annotation = handlerMethod.getBeanType().getAnnotation(IgnoreAuth.class);
        // 如果没有从方法上拿
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
        }
        // 如果不等于空放行
        if (annotation != null) {
            return super.preHandle(request, response, handler);
        }

        String token = request.getHeader(keyConfiguration.getTokenHeader());
        log.info("============== " + token + "==============");
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
