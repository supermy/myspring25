package com.supermy.spring25.aop.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.supermy.spring25.aop.UserBean;
  
public class TestAOP {  
    public static void main(String[] args) {
    	String[] conf={"classpath*:applicationContext-aop.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);  
        UserBean ub = (UserBean)ctx.getBean("userBean");
        System.out.println("test add user");
        ub.addUser("003");  
        System.out.println("test update user");
        ub.updateUser("003", "jamesmo");  
        ub.getUser("003");  
        System.out.println("test get user");
        ub.deleteUser("003"); 
        System.out.println("test del user");
        
		
    }  
}  