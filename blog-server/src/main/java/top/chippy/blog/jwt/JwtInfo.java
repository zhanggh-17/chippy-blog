package top.chippy.blog.jwt;

import lombok.Data;
import lombok.ToString;

/**
 * @program: loser
 * @Author: chippy
 * @Description: token save info
 */
@ToString
@Data
public class JwtInfo {

    private static final long serialVersionUID = -1433617972421094362L;
    private String email;
    private String userId;
    private String name;

    public JwtInfo(String email, String userId, String name) {
        this.email = email;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JwtInfo jwtInfo = (JwtInfo) o;

        if (email != null ? !email.equals(jwtInfo.email) : jwtInfo.email != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
