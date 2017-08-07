package com.github.zhangkaitao.shiro.chapter19.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.zhangkaitao.shiro.chapter19.dao.UserDao;
import com.github.zhangkaitao.shiro.chapter19.entity.Role;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.PasswordHelper;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;


@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService{

	@Autowired public void setDao(UserDao userDao){ super.setBaseDao(userDao);}
	@Autowired private PasswordHelper passwordHelper;

	@Override
	public Long save(User user){
		 passwordHelper.encryptPassword(user);
		 return super.save(user);
	}
	
	@Override
//	@Cacheable(value="service", keyGenerator="keyGenerator")
	public Set<String> findRoles(String username) {
		return findByUsername(username)!=null?findByUsername(username).getRoleNames():Collections.emptySet();
	}

	@Override
//	@Cacheable(value="service", keyGenerator="keyGenerator")
	public Set<String> findPermissions(User user) {
		if(user==null) return Collections.emptySet();
		Set<String> permissionNames = new HashSet<>();
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			if(role.getName().equals("Admin")){
				permissionNames.add("*:*:*");
				break;
			}
			//TODO  findPermission by dao
			permissionNames.addAll(role.getPermissions());
		}
		return permissionNames;
	}

	@Override
	@Cacheable(value="service", key="#username" /**keyGenerator="keyGenerator"*/)
	public User findByUsername(String username) {
		List<User> users = super.findByProperty("username", username);
		return users.size()==0? null: users.get(0);
	}

	@Override
	public void changePassword(Long id, String newPassword) {
	      User user =super.get(id);
	      user.setPassword(newPassword);
	      passwordHelper.encryptPassword(user);
	      update(user);
		
	}
	
	@Override
	@Cacheable(value="service", keyGenerator="keyGenerator")
	public List<User> findAll(){
		return super.findAll();
	}

}
