package com.github.zhangkaitao.shiro.chapter19.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.zhangkaitao.shiro.chapter19.dao.ResourceDao;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource;
import com.github.zhangkaitao.shiro.chapter19.service.ResourceService;


@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource,Long> implements ResourceService{
	@Autowired public void setDao(ResourceDao resourceDao){
		super.setBaseDao(resourceDao);
	}


	    @Override
	    public Set<String> findPermissions(Set<Long> resourceIds) {
	        Set<String> permissions = new HashSet<String>();
	        for(Long resourceId : resourceIds) {
	            Resource resource = get(resourceId);
	            if(resource != null && !StringUtils.isEmpty(resource.getPermissionName())) {
	                permissions.add(resource.getPermissionName());
	            }
	        }
	        return permissions;
	    }

	    @Override
	    public List<Resource> findMenus(Set<String> permissions) {
	        List<Resource> allResources = findAll();
	        List<Resource> menus = new ArrayList<Resource>();
	        for(Resource resource : allResources) {
	            if(resource.isRootNode()) {
	                continue;
	            }
	            if(resource.getType() != Resource.ResourceType.MENU) {
	                continue;
	            }
	            if(!__hasPermission(permissions, resource)) {
	                continue;
	            }
	            menus.add(resource);
	        }
	        return menus;
	    }

	    private boolean __hasPermission(Set<String> permissions, Resource resource) {
	        if(StringUtils.isEmpty(resource.getPermissionName())) {
	            return true;
	        }
	        for(String permission : permissions) {
	            WildcardPermission p1 = new WildcardPermission(permission);
	            WildcardPermission p2 = new WildcardPermission(resource.getPermissionName());
	            if(p1.implies(p2) || p2.implies(p1)) {
	                return true;
	            }
	        }
	        return false;
	    }


	    @Override
	    public List<Resource> findAll(){
	    	return super.findAll();
	    }
}
