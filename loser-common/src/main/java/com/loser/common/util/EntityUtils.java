package com.loser.common.util;

import com.loser.common.constant.ProjectConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 实体类相关工具类 解决问题： 1、快速对实体的常驻字段，如：crtUser、updUser等值快速注入
 *
 * @author K2
 * @version 1.0
 * @date 2016年4月18日
 * @since 1.7
 */
public class EntityUtils {

    private static final String DEFAULT_PACKAGE = "top.chippy.blog.context.BlogContext";

    /**
     * 快速将bean的crtName、crtTime、updName、updTime、delFlag附上相关值
     *
     * @param entity 实体bean
     * @author K2
     */
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * 快速将bean的id、crtName、crtTime、delFlag附上相关值
     *
     * @param entity 实体bean
     * @author K2
     */
    public static <T> void setCreateInfo(T entity) {
        String[] fields = {"crtUser", "crtTime", "id", "delFlag"};
        Field field = ReflectionUtils.getAccessibleField(entity, "crtTime");
        String id = UUIDUtils.generateUuid();
        String username = getEmail();
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{username, DateUtil.getTime(), id, ProjectConstant.SYSTEM_ENABLE};
        } else {
            value = new Object[]{username, DateUtil.getTime(), id, ProjectConstant.SYSTEM_ENABLE};
        }
        setDefaultValues(entity, fields, value);
    }

    /**
     * 快速将bean的updName、updTime附上相关值
     *
     * @param entity 实体bean
     * @author K2
     */
    public static <T> void setUpdatedInfo(T entity) {
        String[] fields = {"updUser", "updTime"};
        Field field = ReflectionUtils.getAccessibleField(entity, "updTime");
        String username = getEmail();
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{username, DateUtil.getTime()};
        } else {
            value = new Object[]{username, DateUtil.getTime()};
        }
        setDefaultValues(entity, fields, value);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * @Description 反射获取用户名
     * @Author chippy
     * @Datetime 2019/1/9 17:01
     */
    public static String getEmail() {
        String email = "";
        try {
            Class<?> cls = Class.forName(DEFAULT_PACKAGE);
            Method getEmail = cls.getDeclaredMethod("getEmail");
            if (!Stringer.isNullOrEmpty(getEmail)) {
                email = String.valueOf(getEmail.invoke(cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }
}
