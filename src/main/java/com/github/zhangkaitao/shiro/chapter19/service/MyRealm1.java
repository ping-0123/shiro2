package com.github.zhangkaitao.shiro.chapter19.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.springframework.stereotype.Service;

@Service
public class MyRealm1 implements Realm{
	
	private static final Log logger = LogFactory.getLog(MyRealm1.class);

	@Override
	public String getName() {
		logger.info("MyRealm1.getName executing....");
		return "myrealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		logger.info("MyRealm1.supports executing");
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("MyRealm1.getAuthenticationInfo executing......");
		String username=(String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		if(!"zhang".equals(username)){
			logger.info("throw UnknwonAccountException");
			throw new UnknownAccountException();
		}
		logger.info("username:" + username + " password:" + password + "  Realm name:" + getName());
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
