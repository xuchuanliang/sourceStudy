package com.ant.myJdkProxy.jdk;

import com.ant.myJdkProxy.v2.IndexDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkInvocationHandler implements InvocationHandler {

    private IndexDao indexDao;
    public JdkInvocationHandler(IndexDao indexDao){
        this.indexDao = indexDao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("jdk before");
        Object result = method.invoke(indexDao,args);
        System.out.println("jdk after");
        return result;

    }
}
