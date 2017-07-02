package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zhangkaitao.shiro.chapter19.entity.Organization;
import com.github.zhangkaitao.shiro.chapter19.entity.Permission;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource;
import com.github.zhangkaitao.shiro.chapter19.entity.Resource.ResourceType;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.OrganizationService;
import com.github.zhangkaitao.shiro.chapter19.service.ResourceService;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
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
	
	@Test
	public void newUser(){
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456");
		user.setOrganization(organizationService.get(1l));
		userService.save(user);
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
