package com.supermy.spring25.busi;

import org.springframework.stereotype.Service;

@Service
public class UserBean {

	public void addUser(String userName) {
		System.out.println("this is addUser() method!");
		// throw new RuntimeException("asdf...");
	}

	public void deleteUser(String userId) {
		System.out.println("this is deleteUser() method!");
	}

	public String getUser(String userId) {
		System.out.println("this is getUser() method!");
		return "haha";
	}

	public void updateUser(String userId, String userName) {
		System.out.println("this is updateUser() method!");
	}
}
