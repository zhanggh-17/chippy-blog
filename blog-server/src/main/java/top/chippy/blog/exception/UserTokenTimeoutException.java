package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @program: loser
 * @Author: chippy
 * @Description: token timeout
 */
public class UserTokenTimeoutException extends BaseException {

    public UserTokenTimeoutException(String message) {
        super(message, CommonConstants.EX_USER_TIMEOUT);
    }

}
