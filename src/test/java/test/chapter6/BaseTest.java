package test.chapter6;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;

import com.github.zhangkaitao.shiro.chapter19.entity.Role;
import com.github.zhangkaitao.shiro.chapter19.entity.User;
import com.github.zhangkaitao.shiro.chapter19.service.RoleService;
import com.github.zhangkaitao.shiro.chapter19.service.UserService;
import com.github.zhangkaitao.shiro.chapter19.service.impl.RoleServiceImpl;
import com.github.zhangkaitao.shiro.chapter19.service.impl.UserServiceImpl;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public abstract class BaseTest {

    protected RoleService roleService = new RoleServiceImpl();
    protected UserService userService = new UserServiceImpl();

    protected String password = "1234";

    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;

    @Before
    public void setUp() {
//        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users");
//        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles");
//        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_permissions");
//        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users_roles");
//        JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles_permissions");

        //1、新增权限
//        p1 = new Permission("user:create", "用户模块新增");
//        p2 = new Permission("user:update", "用户模块修改");
//        p3 = new Permission("menu:create", "菜单模块新增");
//        permissionService.save(p1);
//        permissionService.save(p2);
//        permissionService.save(p3);
//        //2、新增角色
//        r1 = new Role("admin", "管理员", Boolean.TRUE);
//        r2 = new Role("user", "用户管理员", Boolean.TRUE);
//        roleService.createRole(r1);
//        roleService.createRole(r2);
//        //3、关联角色-权限
//        roleService.correlationPermissions(r1.getId(), p1.getId());
//        roleService.correlationPermissions(r1.getId(), p2.getId());
//        roleService.correlationPermissions(r1.getId(), p3.getId());
//
//        roleService.correlationPermissions(r2.getId(), p1.getId());
//        roleService.correlationPermissions(r2.getId(), p2.getId());
//
        //4、新增用户
        u1 = new User("admin", "admin");
        u2 = new User("lisi", "1234");
        u3 = new User("wangwu", password);
        u4 = new User("zhaoliu", password);
//        u4.setLocked(Boolean.TRUE);
//        userService.createUser(u1);
//        userService.createUser(u2);
//        userService.createUser(u3);
//        userService.createUser(u4);
//        //5、关联用户-角色
//        userService.correlationRoles(u1.getId(), r1.getId());

    }




    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

    protected void login(String configFile, String username, String password) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        subject.login(token);
    }

    public Subject subject() {
        return SecurityUtils.getSubject();
    }

}
