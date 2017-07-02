package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.zhangkaitao.shiro.chapter19.entity.Organization;
import com.github.zhangkaitao.shiro.chapter19.entity.Permission;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource.ResourceType;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.OrganizationService;
import com.github.zhangkaitao.shiro.chapter19.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:chapter6/spring-config.xml")
public class UserTest {

	@Autowired UserService userService;
	@Autowired OrganizationService organizationService;
	@Autowired ResourceService resourceService;
	
	@Test
	public void test(){
		Organization org = new Organization();
		org.setName("音之舞");
		organizationService.save(org);
	}
	
//	@Transactional
	@Test
	public void newUser(){
		List<User> users = new ArrayList<User>(); 
		User user = new User("zhang", "123");
		User user2 = new User("lisi", "1234");
		User user3 = new User("wangwu", "1234");
		User user4 = new User("zhaoliu", "1234");
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
}
