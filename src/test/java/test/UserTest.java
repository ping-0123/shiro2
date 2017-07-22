package test;

import java.util.ArrayList;
import java.util.List;

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
import com.github.zhangkaitao.shiro.chapter19.entity.Role;
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
	public void firstUser(){
		User user = new User("admin", "admin");
		// set roles
		Role role = new Role();
		role.setId(1);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		//set organization
		Organization organization = new Organization();
		organization.setId(1);
		user.setOrganization(organization);
		userService.save(user);
		
		
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
