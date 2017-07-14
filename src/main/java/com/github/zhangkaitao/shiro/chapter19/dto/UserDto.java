package com.github.zhangkaitao.shiro.chapter19.dto;

import java.util.ArrayList;
import java.util.List;

import com.github.zhangkaitao.shiro.chapter19.entity.Organization;
import com.github.zhangkaitao.shiro.chapter19.entity.Role;
import com.github.zhangkaitao.shiro.chapter19.entity.User;

/**
*@Author ping
*@Time  创建时间:2017年7月14日下午4:39:30
*
*/

public class UserDto {
	
	private Long id;
	private String username;
	private String password;
	private String salt;
	private Boolean locked=Boolean.FALSE;
	private Organization organization;
	private List<Long> roleIds = new ArrayList<>();
	
	
	public UserDto(){};
	public UserDto(User user){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.id = user.getId();
		this.salt=user.getSalt();
		this.locked = user.getLocked();
		this.organization = user.getOrganization();
		for (Role role : user.getRoles()) {
			this.roleIds.add(role.getId());
		}
	}
	
	public User transferToDao(){
		User  user = new User();
		user.setId(this.id);
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setSalt(this.salt);
		user.setLocked(this.locked);
		user.setOrganization(this.organization);
		List<Role> roles = new ArrayList<>();
		for (Long id : roleIds) {
			Role role = new Role();
			role.setId(id);
			roles.add(role);
		}
		user.setRoles(roles);
		return user;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getSalt() {
		return salt;
	}
	public Boolean getLocked() {
		return locked;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	
}
