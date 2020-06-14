package com.ant.myJdkProxy.v1;

import java.lang.reflect.Proxy;

/**
 * 测试主函数
 */
public class Main {
    public static void main(String[] args){
        IndexDao indexDao = new IndexDaoImpl();
        try {
            IndexDao proxy = (IndexDao)ProxyUtil.getProxy(indexDao, IndexDao.class);
            proxy.test();
            proxy.test("success");
            proxy.testReturn("success",2333);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
