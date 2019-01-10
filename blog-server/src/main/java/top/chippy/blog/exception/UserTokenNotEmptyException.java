package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @program: loser
 * @Author: chippy
 * @Description: token not empty
 */
public class UserTokenNotEmptyException extends BaseException {

    public UserTokenNotEmptyException(String message) {
        super(message, CommonConstants.EX_TOKEN_EMPTY);
    }
}
