package test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.core.io.ClassPathResource;

import com.github.zhangkaitao.shiro.chapter19.entity.User;

import net.sf.ehcache.CacheException;

/**
*@author ping
*@version 创建时间:2017年7月5日下午5:07:26
*
*/

@RunWith(BlockJUnit4ClassRunner.class)
public class SpringCacheTest {

	
	@Test
	public void test(){
		 //创建底层Cache  
	    net.sf.ehcache.CacheManager ehcacheManager = null;
		try {
			ehcacheManager = new net.sf.ehcache.CacheManager(new ClassPathResource("chapter6/ehcache.xml").getInputStream());
		} catch (CacheException | IOException e) {
			e.printStackTrace();
		}  
	  
	    //创建Spring的CacheManager  
	    EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();  
	    //设置底层的CacheManager  
	    cacheCacheManager.setCacheManager(ehcacheManager);  
	  
	    Long id = 1L;  
	    User user = new User( "zhang", "zhang@gmail.com");
	    user.setId(id);
	  
	    //根据缓存名字获取Cache  
	    Cache cache = cacheCacheManager.getCache("passwordRetryCache");  
	    //往缓存写数据  
	    cache.put(id, user);  
	    //从缓存读数据  
//	    Assert.assertNotNull(cache.get(id, User.class))
	    System.out.println(cache.get(id, User.class).getUsername());
	}
}
