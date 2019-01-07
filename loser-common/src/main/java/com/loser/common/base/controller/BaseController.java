package com.loser.common.base.controller;

import com.loser.common.util.Stringer;
import org.springframework.beans.factory.annotation.Autowired;
import com.loser.common.base.entity.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @program: xx
 * @Author: chippy
 * @Description: 基类Controller 封装基本操作
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * @param errorCode    错误代码
     * @param errorMessage 错误信息
     * @return
     * @throws Exception
     * @author by chippy
     * @desc 构造错误的返回信息（带errorCode）.
     */
    protected Object error(Integer errorCode, String errorMessage) {
        return new ResponseEntity(errorCode, errorMessage).toJson();
    }

    /**
     * @param errorMessage 错误信息
     * @return
     * @throws Exception
     * @author by chippy
     * @desc 构造错误的返回信息（不带errorCode）.
     */
    protected Object error(String errorMessage) {
        return new ResponseEntity(null, errorMessage).toJson();
    }

    /**
     * @param data - 业务数据json
     * @return
     * @throws Exception
     * @author by chippy
     * @desc 构造成功的返回信息.
     */
    protected Object success(Object data) {
        return new ResponseEntity(data).toJson();
    }

    /**
     * @param obj
     * @return
     * @author chippy
     * @desc 判断某对象是否为空..
     */
    protected boolean isNullOrEmpty(Object obj) {
        return Stringer.isNullOrEmpty(obj);
    }

    /**
     * @param request
     * @return
     * @author chippy
     * @desc 获取webapp完整URL. e.g http://www.abc.com/app/a/b/c?a=b&c=d...
     */
    protected final String getRequestURL(HttpServletRequest request) {

        if (request == null) {
            return "";
        }

        String url = "";
        url = "http://" + request.getServerName() // 服务器地址
                // + ":"
                // + request.getServerPort() //端口号
                + request.getContextPath() // 项目名称
                + request.getServletPath(); // 请求页面或其他地址
        try {
            // 参数
            Enumeration<?> names = request.getParameterNames();

            int i = 0;
            String queryString = request.getQueryString();
            if (null != queryString && !"".equals(queryString) && (!queryString.equals("null"))) {
                url = url + "?" + request.getQueryString();
                i++;
            }

            if (names != null) {
                while (names.hasMoreElements()) {
                    if (i == 0) {
                        url = url + "?";
                    }

                    String name = (String) names.nextElement();
                    if (url.indexOf(name) < 0) {
                        url = url + "&";

                        String value = request.getParameter(name);
                        if (value == null) {
                            value = "";
                        }
                        url = url + name + "=" + value;
                        i++;
                    }
                    // java.net.URLEncoder.encode(url, "ISO-8859");
                }
            }

            // String enUrl = java.net.URLEncoder.encode(url, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return url;
    }

}
