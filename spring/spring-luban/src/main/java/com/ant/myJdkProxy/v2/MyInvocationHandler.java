package com.test1.ant.myJdkProxy.v2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface MyInvocationHandler {

    /**
     * 模拟jdk InvocationHandler，实际上为了能够让用户自定义代理逻辑，提供给用户的钩子函数，由用户定义代理逻辑与真实方法调用的顺序
     * 当用户获取到代理对象调用方法时，代理对象方法中实际上是调用本方法
     * @param method
     * @param args
     * @return
     */
    Object invoke(Object target,Method method,Object...args) throws InvocationTargetException, IllegalAccessException;

}
