package com.ant.myJdkProxy;

/**
 * 接口，模拟有无返回值、有无参数多种情况方法
 */
public interface IndexDao {
    void test();
    void test(String s);
    String testReturn(String s,Integer i);
}