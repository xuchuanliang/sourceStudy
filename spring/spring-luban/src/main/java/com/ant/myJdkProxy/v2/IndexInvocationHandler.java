package com.test1.ant.myJdkProxy.v2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IndexInvocationHandler implements MyInvocationHandler {
    @Override
    public Object invoke(Object target, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("方法执行开始前");
        Object o = method.invoke(target, args);
        System.out.println("方法执行开始后");
        return o;

    }
}
