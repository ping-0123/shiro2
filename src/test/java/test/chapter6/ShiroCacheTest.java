package test.chapter6;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
*@author ping
*@version 创建时间:2017年7月8日下午3:15:39
*
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:chapter6/spring-config.xml")
public class ShiroCacheTest {

	@Autowired private SecurityManager securityManager;
	
	@Test
	public void test(){
		 SecurityUtils.setSecurityManager(securityManager);
		 Subject subject = SecurityUtils.getSubject();
		 UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
		 subject.login(token);
		 
	}
}
