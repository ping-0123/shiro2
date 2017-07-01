package com.github.zhangkaitao.shiro.chapter19.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zhangkaitao.shiro.chapter19.dao.PermissionDao;
import com.github.zhangkaitao.shiro.chapter19.entity.Permission;
import com.github.zhangkaitao.shiro.chapter19.service.PermissionService;



@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements PermissionService{

	@Autowired public void setDao(PermissionDao dao){ super.setBaseDao(dao);}
}
