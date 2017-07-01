package com.github.zhangkaitao.shiro.chapter19.service;

import java.util.List;
import java.util.Set;

import com.github.zhangkaitao.shiro.chapter19.entity.Resource;


public interface ResourceService extends BaseService<Resource,Long> {

	List<Resource> findMenus(Set<String> permissions);

	Set<String> findPermissions(Set<Long> resourceIds);

}
