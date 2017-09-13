package test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.PasswordHelper;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;

import test.TestBase;

/**
*@Author ping
*@Time  创建时间:2017年9月13日下午4:32:58
*
*/

public class UserServiceTest extends TestBase{
	
	@Autowired private UserService userService;
	@Autowired private PasswordHelper passwordHelper;
	
	@Test
	@Rollback(value=false)
	public void newAdmin(){
		User user = new User("admin","admin");
		passwordHelper.encryptPassword(user);
		userService.save(user);
	}
}
