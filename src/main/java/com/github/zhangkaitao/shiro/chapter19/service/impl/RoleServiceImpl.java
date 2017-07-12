package com.github.zhangkaitao.shiro.chapter19.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zhangkaitao.shiro.chapter19.dao.RoleDao;
import com.github.zhangkaitao.shiro.chapter19.entity.Role;
import com.github.zhangkaitao.shiro.chapter19.service.RoleService;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService{

	@Autowired public void setDao(RoleDao dao){ super.setBaseDao(dao);}
	
}
