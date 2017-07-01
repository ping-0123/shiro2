package com.github.zhangkaitao.shiro.chapter19.dao;

import com.github.zhangkaitao.shiro.chapter19.entity.User;

public interface UserDao extends BaseDao<User, Long> {

	User findByUserName(String loginName);

}
