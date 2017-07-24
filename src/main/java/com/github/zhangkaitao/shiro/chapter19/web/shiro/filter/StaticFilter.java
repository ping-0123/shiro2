package com.github.zhangkaitao.shiro.chapter19.web.shiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.web.util.WebUtils;

/**
*@Author ping
*@Time  创建时间:2017年7月24日上午10:46:36
*
*/

public class StaticFilter implements Filter{
	
	private static final Log logger = LogFactory.getLog(StaticFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		String path = WebUtils.getPathWithinApplication(request);
		if(path !=null){
			if(logger.isDebugEnabled())
				logger.debug("do not filter static resource: " + path);
			request.getRequestDispatcher(path).forward(request, response);
		}else {
			arg2.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
