package com.loser.common.util;

import com.loser.common.exception.BaseException;

import java.lang.reflect.Field;

/**
 * @author : Storydo
 * @Title: BeanPropertiesUtil.java
 * @Description: 批量处理对象的属性
 * @date: 2017年6月9日 上午9:19:33
 * @version:V1.0 Copyright 悦享互联 2016 All right reserved.
 * Modification  History:
 * Version       Date          Author          Description
 * ----------------------------------------------------------------------------
 * 1.0         2017年6月9日        Storydo              TODO
 */

public class BeanPropertiesUtil {

    /**
     * @param obj        要查询的对象
     * @param fieldNames 指定要查询的字段名称数组
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @Description: 如果查询对象指定的所有字段都不为空，则返回true，否则异常
     * @author: Storydo
     * @date: 2017年6月6日 上午11:06:40
     */
    public static boolean fieldsNotNullOrEmpty(Object obj, String[] fieldNames) throws IllegalArgumentException, IllegalAccessException {
        Class<?> cls = obj.getClass();

        for (String fieldName : fieldNames) {
            Field field = getBeanProperty(cls, fieldName);
            Object value = field.get(obj);
            if (Stringer.isNullOrEmpty(value)) {
                //System.out.println("------" + fieldName + "---空");
                throw new BaseException(fieldName + "不能为空!", 200);
            }
        }

        return true;
    }

    /**
     * @param obj
     * @param fieldNames
     * @return
     * @Description: 将对象中指定的所有字段置空，成功返回true
     * @author: Storydo
     * @date: 2017年6月6日 上午11:16:02
     */
    public static boolean setFieldsNull(Object obj, String[] fieldNames) {
        Class<?> cls = obj.getClass();
        try {
            for (String fieldName : fieldNames) {
                Field field = getBeanProperty(cls, fieldName);
                field.set(obj, null);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param source     源对象
     * @param target     目标对象
     * @param fieldNames 字段名
     * @return
     * @Description: 从源对象拷贝属性值到目标对象 属性值不能为基本数据类型
     * @author: Storydo
     * @date: 2017年6月9日 上午9:51:05
     */
    public static boolean copyFields(Object source, Object target, String[] fieldNames) {
        Class<?> sCls = source.getClass();
        Class<?> tCls = target.getClass();
        try {
            for (String fieldName : fieldNames) {
                Field tField = getBeanProperty(tCls, fieldName);
                Field sField = getBeanProperty(sCls, fieldName);
                tField.set(target, sField.get(source));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static Field getBeanProperty(Class<?> cls, String fieldName) {
        Field field = null;
        try {
            field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (Exception e) {
            // TODO: handle exception
            if (cls.getSuperclass() != Object.class)
                field = getBeanProperty(cls.getSuperclass(), fieldName);

        }
        return field;
    }
}
