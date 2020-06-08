package com.ant.proxy.extend;

import com.ant.proxy.target.UserDaoImpl;

/**
 * 通过继承实现代理
 */
public class UserLogDaoImpl extends UserDaoImpl {
    @Override
    public void query() {
        System.out.println("增加日志");
        super.query();
    }
}
