package top.chippy.blog.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.chippy.blog.config.KeyConfiguration;
import top.chippy.blog.exception.UserTokenErrorFormatException;
import top.chippy.blog.exception.UserTokenException;
import top.chippy.blog.exception.UserTokenTimeoutException;

/**
 * @Description Jwt token util.Generate token for user.Get user info by token.
 * @Author chippy
 */
@Component
@Lazy
public class JwtTokenUtil {

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Value("${jwt.expire}")
    private int expire;

    public String generateToken(JwtInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getPriKey(), expire);
    }

    public JwtInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, keyConfiguration.getPubKey());
        } catch (ExpiredJwtException ex) {
            throw new UserTokenTimeoutException("用户认证超时,请重新进行认证");
        } catch (SignatureException ex) {
            throw new UserTokenException("用户令牌认证失败");
        } catch (MalformedJwtException ex) {
            throw new UserTokenErrorFormatException("令牌格式不正确");
        }
    }

}
