package com.github.zhangkaitao.shiro.chapter19.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zhangkaitao.shiro.chapter19.dao.UrlFilterDao;
import com.github.zhangkaitao.shiro.chapter19.entity.UrlFilter;
import com.github.zhangkaitao.shiro.chapter19.service.UrlFilterService;


@Service
public class UrlFilterServiceImpl extends BaseServiceImpl<UrlFilter, Long> implements UrlFilterService {
	
	@Autowired public void setDao(UrlFilterDao urlFilterDao){
		super.setBaseDao(urlFilterDao);
	}

}
