package com.supermy.spring25.aop;

import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.After;  
import org.aspectj.lang.annotation.AfterReturning;  
import org.aspectj.lang.annotation.AfterThrowing;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;  
import org.aspectj.lang.annotation.Pointcut;  
  
@Aspect  
public class MyInterceptor {  
    /** 
     * 定义切入点 
     * 第一个*表示方法的返回值,这里使用通配符,只有返回值符合条件的才拦截,(!void表示有返回值) 
     * 第一个..表示com.royzhou.aop包及其子包 
     * 倒数第二个*表示包下的所有Java类都被拦截 
     * 最后一个*表示类的所有方法都被拦截 
     * (..)表示方法的参数可以任意多个如[(java.lang.String,java.lang.Integer)表示第一个参数是String,第二个参数是int的方法才会被拦截] 
     */  
    @Pointcut("execution(* com.supermy.spring25.aop..*.*(..)) || execution(* com.supermy..*.busi..*.*(..)) ") //定义一个切入点,名称为pointCutMethod(),拦截类的所有方法  
    private void pointCutMethod() {
    	 System.out.println("通知"); 
    }  
      
    @Before("pointCutMethod()") //定义前置通知  
    public void doBefore() {  
        System.out.println("前置通知");  
    }  
      
    @AfterReturning("pointCutMethod()") //定义后置通知  
    public void doAfterReturning() {  
        System.out.println("后置通知");  
    }  
      
    @AfterThrowing("pointCutMethod()") //定义例外通知  
    public void doAfterException() {  
        System.out.println("异常通知");  
    }  
      
    @After("pointCutMethod()") //定义最终通知  
    public void doAfter() {  
        System.out.println("最终通知");  
    }  
      
    @Around("pointCutMethod()") //定义环绕通知  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
        System.out.println("进入方法");  
        Object object = pjp.proceed(); //必须执行pjp.proceed()方法,如果不执行此方法,业务bean的方法以及后续通知都不执行  
        System.out.println("退出方法");  
        return object;  
    }  
} 
