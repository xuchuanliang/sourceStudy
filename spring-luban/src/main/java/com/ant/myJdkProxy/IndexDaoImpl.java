package com.ant.myJdkProxy;

/**
 * 接口实现类
 */
public class IndexDaoImpl implements IndexDao{
    @Override
    public void test() {
        System.out.println("test()");
    }

    @Override
    public void test(String s) {
        System.out.println("Test("+s+")");
    }

    @Override
    public String testReturn(String s, Integer i) {
        System.out.println("testReturn("+s+","+i);
        return "hshsh";
    }
}
