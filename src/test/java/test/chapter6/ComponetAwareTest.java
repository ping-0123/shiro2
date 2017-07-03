package test.chapter6;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zhangkaitao.shiro.chapter19.realm.UserRealm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:chapter6/spring-config.xml")
public class ComponetAwareTest {
	private static final Log Logger = LogFactory.getLog(ComponetAwareTest.class);
	
	@Autowired private UserRealm userRealm;
	
	@Test
	public void test(){
		Logger.error(userRealm.findUser("admin").getSalt());
		System.out.println(userRealm.getClass());
	}
}
