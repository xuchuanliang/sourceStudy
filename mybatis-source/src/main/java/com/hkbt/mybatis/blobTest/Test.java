package com.hkbt.mybatis.blobTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        /*
        SqlSession 作为MyBatis工作的主要顶层API，表示和数据库交互的会话，完成必要数据库增删改查功能
        Executor MyBatis执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护
        StatementHandler 封装了JDBC Statement操作，负责对JDBC statement 的操作，如设置参数、将Statement结果集转换成List集合。
        ParameterHandler 负责对用户传递的参数转换成JDBC Statement 所需要的参数，
        ResultSetHandler 负责将JDBC返回的ResultSet结果集对象转换成List类型的集合；
        TypeHandler 负责java数据类型和jdbc数据类型之间的映射和转换
        MappedStatement MappedStatement维护了一条<select|update|delete|insert>节点的封装，
        SqlSource 负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象中，并返回
        BoundSql 表示动态生成的SQL语句以及相应的参数信息
        Configuration MyBatis所有的配置信息都维持在Configuration对象之中。
         */
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sessionFactory.openSession();
        BlobMapper blobMapper = sqlSession.getMapper(BlobMapper.class);
        List<BaoanVo> hikpsis = blobMapper.getInfo("hikpsis",2);
        System.out.println(hikpsis);
    }
}
