package com.hkbt.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
//        List<Blog> all = mapper.findAll();
//        all.forEach(System.out::println);

        Class.forName("com.mysql.jdbc.Driver");
        for(int i=0;i<11;i++){
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/t?user=root&password=123456");
            connection.setAutoCommit(false);
            System.out.println(connection.nativeSQL("select * from t;"));
            System.out.println(i);
            connection.commit();
        }
        System.out.print("成功1");
    }
}
