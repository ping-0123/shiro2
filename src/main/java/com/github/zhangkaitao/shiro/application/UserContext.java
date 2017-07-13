package com.github.zhangkaitao.shiro.application;

import com.github.zhangkaitao.shiro.chapter19.entity.User;

/**
*@Author ping
*@Time  创建时间:2017年7月13日下午1:35:57
*
*/

public class UserContext {

	private static ThreadLocal<User> tl = new ThreadLocal<>();
	
	public static void setUser(User user){
		tl.set(user);
	}
	
	public static User getUser(){
		return tl.get();
	}
}
