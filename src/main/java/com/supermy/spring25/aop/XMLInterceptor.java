package com.supermy.spring25.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.supermy.spring25.log.Log2DB;
import com.supermy.spring25.log.SystemThreadLocalMap;

public class XMLInterceptor {
	private final Logger log = Logger.getLogger(XMLInterceptor.class);
	
	@Autowired
	private Log2DB log2DB;
	
	@Autowired
	private SystemThreadLocalMap threadLocalMap;// 用户信息


	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		
		String operationMethodName = pjp.getSignature().getName();
		
		String packageName=pjp.getSignature().getDeclaringTypeName();
		
		System.out.println("XML 进入方法:"+packageName+"."+operationMethodName);
		long start = System.currentTimeMillis();
		Object object = pjp.proceed(); // 必须执行pjp.proceed()方法,如果不执行此方法,业务bean的方法以及后续通知都不执行
		long end = System.currentTimeMillis();
		log2DB.execAsyncAddUserLog(threadLocalMap.getUserName(),(end-start));
		
		System.out.println("XML 退出方法");
		return object;
	}

	

}
