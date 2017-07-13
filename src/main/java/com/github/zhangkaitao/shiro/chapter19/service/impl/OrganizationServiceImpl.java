package com.github.zhangkaitao.shiro.chapter19.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zhangkaitao.shiro.chapter19.dao.OrganizationDao;
import com.github.zhangkaitao.shiro.chapter19.entity.Organization;
import com.github.zhangkaitao.shiro.chapter19.exception.DataNotFoundException;
import com.github.zhangkaitao.shiro.chapter19.service.OrganizationService;


@Service
public class OrganizationServiceImpl extends BaseServiceImpl<Organization,Long> implements OrganizationService{
	@Autowired public void setDao(OrganizationDao dao){
		super.setBaseDao(dao);
	}

	@Override
	public List<Organization> findAllWithExclude(Organization source) {
		List<Organization> orgs = findAll();
		orgs.removeAll(source.getAllChilds());
		orgs.remove(source);
		return orgs;
	}

	@Override
	public void move(Organization source, Organization target) {
		source.setParent(target);
		update(source);
	}


}
