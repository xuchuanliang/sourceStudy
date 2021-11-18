package com.hkbt.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void initSqlSessionFactory() throws IOException {
        String resource = "mybatis-con fig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @org.junit.Test
    public void test1(){
        SqlSession sqlSession = null;
        try{
            sqlSession = sqlSessionFactory.openSession();
            TestMapper mapper = sqlSession.getMapper(TestMapper.class);
            List<Blog> all = mapper.findAll();
            all.forEach(System.out::println);
        }finally {
            sqlSession.close();
        }
    }
}
