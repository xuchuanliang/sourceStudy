package com.ant.proxy;

import com.ant.proxy.extend.UserLogDaoImpl;
import com.ant.proxy.target.UserDao;
import com.ant.proxy.target.UserDaoImpl;

public class Main {
    public static void main(String[] args) {
        //通过继承实现代理
        UserDao userDao = new UserLogDaoImpl();
        userDao.query();
        //通过聚合实现代理
        UserDao target = new UserDaoImpl();
        UserDao proxy = new com.ant.proxy.implement.UserLogDaoImpl(target);
        proxy.query();
    }
}
