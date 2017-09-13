package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
*@Author ping
*@Time  创建时间:2017年8月16日下午5:33:21
*
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
@Transactional(value = "transactionManager")
public abstract class TestBase {
//public abstract class TestBase extends AbstractTransactionalJUnit4SpringContextTests {
	protected  Log logger = LogFactory.getLog(getClass());
}
