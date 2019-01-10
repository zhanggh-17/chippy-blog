package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @Date: 2018/11/20 21:38
 * @program: loser
 * @Author: chippy
 * @Description:
 */
public class UserPasswordErrorException extends BaseException {
    public UserPasswordErrorException(String message) {
        super(message, CommonConstants.EX_PASSWORD_ERROR);
    }
}
