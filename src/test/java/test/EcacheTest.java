package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zhangkaitao.shiro.chapter19.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class EcacheTest {

	@Autowired UserService userService;
	
	@Test
	public void test(){
		System.out.println(userService.get(1l));
		System.out.println("second find");
	
	}
	
	@Test
	public void test2(){
		System.out.println(userService.get(1l).getUsername());
	}
}
