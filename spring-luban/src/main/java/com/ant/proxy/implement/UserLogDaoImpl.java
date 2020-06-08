package com.ant.proxy.implement;

import com.ant.proxy.target.UserDao;

/**
 * 通过聚合实现代理：即通过实现相同的接口，并且持有目标对象的一个引用，达到代理的作用
 */
public class UserLogDaoImpl implements UserDao {

    private final UserDao userDao;

    public UserLogDaoImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void query() {
        System.out.println("聚合方式日志记录");
        userDao.query();
    }
}
