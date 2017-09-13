package test;

import org.apache.shiro.util.AntPathMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class AntPathMatchTest {

	@Test
	public void testDoMatch(){
		AntPathMatcher matcher = new AntPathMatcher();
		String source = "/";
		String pattern = "/";
		if(matcher.matches(pattern, source)){
			System.out.println("matches");
		}
	}
}
