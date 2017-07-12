package com.github.zhangkaitao.shiro.chapter19.service;

import java.util.Set;

import com.github.zhangkaitao.shiro.chapter19.entity.User;


public interface UserService extends BaseService<User, Long> {

	Set<String> findRoles(String username);

//	Set<String> findPermissions(String username);

	User findByUsername(String username);

	void changePassword(Long id, String newPassword);

	Set<String> findPermissions(User user);

}
