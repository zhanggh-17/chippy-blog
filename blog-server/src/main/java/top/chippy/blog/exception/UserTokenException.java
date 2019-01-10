package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @program: loser
 * @Author chippy
 * @Description user token exception
 */
public class UserTokenException extends BaseException {

    public UserTokenException(String message) {
        super(message, CommonConstants.EX_TOKEN_ERROR_CODE);
    }
    
}