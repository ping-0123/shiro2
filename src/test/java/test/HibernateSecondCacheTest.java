package test;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.mysql.jdbc.log.LogFactory;

import net.sf.ehcache.search.expression.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class HibernateSecondCacheTest {

	private static final Log logger = org.apache.commons.logging.LogFactory.getLog(HibernateSecondCacheTest.class);
	
	@Autowired SessionFactory factory;
	
	@Test
	public void testFactory(){
		Session session = factory.openSession();
		session.getTransaction().begin();
		User user = session.get(User.class, 12l);
		logger.info("in session1: " + user.getOrganization().getName());
		session.getTransaction().commit();
//		session.close();
		
		Session session2 = factory.openSession();
		session2.getTransaction().begin();
		User user2 = session2.get(User.class, 12l);
		System.out.println("in session2: " + user2.getOrganization().getName());
		session2.getTransaction().commit();
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	@Test
	public void testFindAll(){
		Session session = factory.openSession();
		session.getTransaction().begin();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Path<User> root = criteria.from(User.class);
		criteria.select(root);
		List<User> users1 = session.createQuery(criteria).getResultList();
		logger.info("in session1: " + users1.size());
		session.getTransaction().commit();
//		session.close();
		
		Session session2 = factory.openSession();
		session2.getTransaction().begin();
		List<User> users2 = session2.createQuery("from User",User.class).getResultList();
		logger.info("in session2: " + users2.size());
		session2.getTransaction().commit();
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
