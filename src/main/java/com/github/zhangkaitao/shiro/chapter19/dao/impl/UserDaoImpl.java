package com.github.zhangkaitao.shiro.chapter19.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.zhangkaitao.shiro.chapter19.dao.UserDao;
import com.github.zhangkaitao.shiro.chapter19.entity.User;



@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	@Override
	public User findByUserName(String loginName) {
		List<User> users = findByProperty("name", loginName);
		if(users != null && users.size()==1)
			return users.get(0);
		return null;
		
	}

}
