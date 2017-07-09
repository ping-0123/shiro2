package test;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class WildcardPermissionTest {

	@Test
	public void test(){
		
		WildcardPermission w1 = new WildcardPermission("user:create,update",false);
		System.out.println(w1);
		WildcardPermission w2 = new WildcardPermission("user:create:1", false);
		System.out.println("w2" + w2);
		if(w1.implies(w2))
			System.err.println("yes");
		else {
			System.err.println("no");
		}
	}
}
