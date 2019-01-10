package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @Date: 2018/11/20 21:19
 * @program: loser
 * @Author: chippy
 * @Description:
 */
public class UserNameNotExistsException extends BaseException {

    public UserNameNotExistsException(String message) {
        super(message, CommonConstants.EX_USERNAME_EMPTY);
    }

}
