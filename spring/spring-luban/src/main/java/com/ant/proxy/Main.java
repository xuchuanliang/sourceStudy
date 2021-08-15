package com.test1.ant.proxy;

import com.test1.ant.proxy.extend.UserLogDaoImpl;
import com.test1.ant.proxy.target.UserDao;
import com.test1.ant.proxy.target.UserDaoImpl;

public class Main {
    public static void main(String[] args) {
        //通过继承实现代理
        UserDao userDao = new UserLogDaoImpl();
        userDao.query();
        //通过聚合实现代理
        UserDao target = new UserDaoImpl();
        UserDao proxy = new com.test1.ant.proxy.implement.UserLogDaoImpl(target);
        proxy.query();
    }
}
