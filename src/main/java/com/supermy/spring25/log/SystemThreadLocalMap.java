package com.supermy.spring25.log;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 用户信息传递到逻辑层
 * 
 * @author jamesmo
 * @version 创建时间：2011-12-23 下午6:04:07
 * 
 */
@Service
public class SystemThreadLocalMap extends ThreadLocal<Map<String, String>> {

	private static final ThreadLocal username = new ThreadLocal();
	private static final ThreadLocal userid = new ThreadLocal();

	public static String getUserName() {
		String s = (String) username.get();
		if (s == null) {
			s = "未知";
			username.set(s);
		}
		return s;
	}

	public static void setUserName(String s) {
		username.set(s);
	}

}
