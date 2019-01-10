package top.chippy.blog.exception;

import com.loser.common.exception.BaseException;
import top.chippy.blog.constant.CommonConstants;

/**
 * @Date: 2018/11/20 20:36
 * @program: loser
 * @Author: chippy
 * @Description:
 */
public class UserTokenIsEmptyException extends BaseException {

    public UserTokenIsEmptyException(String message) {
        super(message, CommonConstants.EX_SERVER_EMPTY);
    }

}
