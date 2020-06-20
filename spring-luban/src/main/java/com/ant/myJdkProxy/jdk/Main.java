package com.ant.myJdkProxy.jdk;

import com.ant.myJdkProxy.v2.IndexDao;
import com.ant.myJdkProxy.v2.IndexDaoImpl;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler(new IndexDaoImpl());
        IndexDao instance = (IndexDao) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{IndexDao.class}, jdkInvocationHandler);
        System.out.println(instance);
    }
}
