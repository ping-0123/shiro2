package com.github.zhangkaitao.shiro.chapter19.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zhangkaitao.shiro.chapter19.Constants;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UserService userService;
//    @Autowired private SpringCacheManagerWrapper cacheManager;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        User user =null;
        if(username!=null && !"".equals(username.trim())){
        	user = userService.findByUsername(username);
        }
        request.setAttribute(Constants.CURRENT_USER, user);
        return true;
    }
}
