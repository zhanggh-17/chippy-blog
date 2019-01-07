package com.loser.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * Http 获取 ip地址
 * 
 * 获取简单的请求头
 * @author chippy
 *
 */
public class ClientUtil {
	/**
	 * 获取客户端真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取基本请求头get // String token
	 * 
	 * @return
	 */
	public static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return headers;
	}

	/**
	 * 获取基本请求头post String token
	 * 
	 * @return
	 */
	public static HttpHeaders postHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return headers;
	}
}
