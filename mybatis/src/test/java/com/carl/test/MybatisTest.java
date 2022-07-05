package com.carl.test;

import com.carl.test.Dao.IUserDao;
import com.carl.test.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    public static void main(String[] args) throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生成SQlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
//        List<User> all = userDao.findAll();
//        all.forEach(System.out::println);
        List<User> namel = userDao.findByName("",3,"man");
        namel.forEach(System.out::println);
        //6.释放资源
        session.close();
        in.close();
//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
