package test;

import org.junit.Test;

import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.PasswordHelper;

public class PasswordHelperTest {

	@Test
	public void test(){
		User user = new User("admin", "admin");
		PasswordHelper helper = new PasswordHelper();
		helper.setAlgorithmName("md5");
		helper.setHashIterations(2);
		helper.encryptPassword(user);
		
		System.out.println(user.getCredentialsSalt());
		System.err.println("password: " + user.getPassword());
	}
}
