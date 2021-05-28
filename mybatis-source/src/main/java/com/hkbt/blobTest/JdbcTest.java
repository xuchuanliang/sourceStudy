package com.hkbt.blobTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static void main(String[] args) {
        try {
            //测试jdbc查询数据库
            String url = "jdbc:mysql://localhost:3306/mybatis_source?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";
            String sql = "select id from hikpsis_exchange.baoan_filter;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println(resultSet.getLong("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
