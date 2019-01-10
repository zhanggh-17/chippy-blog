package top.chippy.blog.jwt;

import com.loser.common.util.Stringer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import top.chippy.blog.constant.CommonConstants;

/**
 * @Author chippy
 */
public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    public static String generateToken(JwtInfo jwtInfo, byte priKey[], Integer expire) throws Exception {
        String compactJws = Jwts.builder().setSubject(jwtInfo.getEmail())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getUserId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey)).compact();
        return compactJws;
    }

    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        return claimsJws;
    }

    public static JwtInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new JwtInfo(body.getSubject(), Stringer.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)),
                Stringer.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }
}
