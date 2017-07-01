package com.github.zhangkaitao.shiro.chapter19.service;

import java.util.List;

import com.github.zhangkaitao.shiro.chapter19.entity.Organization;


public interface OrganizationService extends BaseService<Organization,Long> {

	List<Organization> findAllWithExclude(Organization source);

	void move(Organization source, Organization target);

}
