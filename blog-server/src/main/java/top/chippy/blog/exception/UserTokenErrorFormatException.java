package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @program: loser
 * @Author: chippy
 * @Description: token format ex
 */
public class UserTokenErrorFormatException extends BaseException {

    public UserTokenErrorFormatException(String message) {
        super(message, CommonConstants.EX_ERROR_FORMAT);
    }
    
}
