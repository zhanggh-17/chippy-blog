package top.chippy.blog.context;

import com.loser.common.util.Stringer;
import top.chippy.blog.constant.CommonConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: loser
 * @Author: chippy
 * @Description:
 */
public class BlogContext {

    protected static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void setUserID(String userId) {
        set(CommonConstants.CONTEXT_KEY_USER_ID, userId);
    }

    public static void setEmail(String email) {
        set(CommonConstants.CONTEXT_KEY_EMAIL, email);
    }

    public static void setName(String name) {
        set(CommonConstants.CONTEXT_KEY_USER_NAME, name);
    }

    public static void setToken(String token) {
        set(CommonConstants.CONTEXT_KEY_USER_TOKEN, token);
    }

    public static String getUserId() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static String getEmail() {
        Object value = get(CommonConstants.CONTEXT_KEY_EMAIL);
        return returnObjectValue(value);
    }

    public static String getUserName() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_NAME);
        return Stringer.getObjectValue(value);
    }

    public static String getToken() {
        Object value = get(CommonConstants.CONTEXT_KEY_USER_TOKEN);
        return Stringer.getObjectValue(value);
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static void remove() {
        threadLocal.remove();
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }
}
