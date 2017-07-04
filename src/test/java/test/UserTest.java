package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zhangkaitao.shiro.chapter19.credentials.RetryLimitHashedCredentialsMatcher;
import com.github.zhangkaitao.shiro.chapter19.entity.Organization;
import com.github.zhangkaitao.shiro.chapter19.entity.Permission;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource.ResourceType;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.realm.UserRealm;
import com.github.zhangkaitao.shiro.chapter19.service.OrganizationService;
import com.github.zhangkaitao.shiro.chapter19.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:chapter6/spring-config.xml")
public class UserTest {

	private static final Log logger = LogFactory.getLog(UserTest.class);
	
	@Autowired UserService userService;
	@Autowired OrganizationService organizationService;
	@Autowired ResourceService resourceService;
	@Autowired
	private SecurityManager securityManager;
	@Autowired private RetryLimitHashedCredentialsMatcher credentialMatcher;
	@Autowired private UserRealm userRealm;
	
	@Test
	public void testUserRealmAware(){
		logger.info(credentialMatcher.getClass());
		logger.info(credentialMatcher.getHashAlgorithmName());
		logger.info(credentialMatcher.getHashIterations());
	}
	
	@Test
	public void isAwared(){
		logger.info(securityManager);
	}
	
	@Test
	public void test(){
		Organization org = new Organization();
		org.setName("音之舞");
		organizationService.save(org);
	}
	
//	@Transactional
	@Test
	public void newUser(){
		User user = new User("yiping", "suning0987");
		userService.save(user);
		
//		userService.save(user2);
//		userService.save(user3);
//		userService.save(user4);
		
	}
	
	@Test
	public void addResource(){
		Resource r = new Resource("导航", ResourceType.BUTTON,null,null);
		resourceService.save(r);
		Permission pom = new Permission("organization:*", "ORGANIZATION_ADMIN");
		Resource rom = new Resource("组织机构管理",ResourceType.MENU,r, pom);
		Permission poc = new Permission("organization:create", "ORGANIZATION_CREATE");
	}
	
	
	
	@Test
	public void testLogin(){
		logger.info("start login......");
		login("wangwu", "1234");
		logger.info("login seccuss.....");
	}
	
	protected void login(String username, String password){
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
	}
}
