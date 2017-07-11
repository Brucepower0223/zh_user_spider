import com.whut.dao.DepartmentMapper;
import com.whut.dao.UserMapper;
import com.whut.dao.ZhUserMapper;
import com.whut.spider.entity.Department;
import com.whut.spider.entity.User;
import com.whut.spider.entity.ZhUser;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fangjin on 2017/7/4.
 */
public class MyBatisTest {

    @Resource
    SqlSessionFactory sessionFactory;

    @Autowired
    ZhUserMapper zhUserMapper = null;

    SqlSession sqlSession;

    @Before
    public void init() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context.xml");
        sessionFactory = (SqlSessionFactory) context.getBean("sessionFactory");
        sqlSession = sessionFactory.openSession();
    }

    @Test
    public void testInsert() throws SQLException {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            ZhUser zhUser = new ZhUser();
            zhUser.setName("Bruce");
            ZhUserMapper zhUserMapper = sqlSession.getMapper(ZhUserMapper.class);
            System.out.println(zhUserMapper);
            zhUserMapper.insert(zhUser);
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testSelectUser() {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.selectUser(3);
            System.out.println(user.toString());
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testInsertUser() {
        SqlSession sqlSession = sessionFactory.openSession();
        try {
            User user = new User();
            user.setName("Elaine");
            user.setAge(23);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            System.out.println(user.getId());
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void insertDepartment() {
        Department department = new Department();
        department.setName("product");
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        departmentMapper.addDepartment(department);
    }


    @Test
    public void testUserAndDepartment() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectConcreteUser(2);
        System.out.println(user);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = sqlSession.selectList("com.whut.dao.UserMapper.getAllUsers");
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = userMapper.getAllUsers();
        System.out.println(users.size());

    }

    @After
    public void close() {
        sqlSession.close();
    }

}
