package com.test1.ant.proxy.target;

public class UserDaoImpl implements UserDao{

    @Override
    public void query() {
        System.out.println("查询数据库");
    }
}
