package com.test1.ant.myJdkProxy.v2;

import com.test1.ant.myJdkProxy.jdk.JdkInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 测试主函数
 * 经过测试我们自己写的动态代理执行时间是jdk动态代理的97倍
 */
public class Main {
    public static void main(String[] args){
        IndexDao indexDao = new IndexDaoImpl();
        try {
            //1973982603
            long start1 = System.nanoTime();
            IndexDao proxy = (IndexDao) ProxyUtil.getProxy(indexDao, IndexDao.class, new IndexInvocationHandler() );
            proxy.test();
            proxy.test("2333");
            proxy.testReturn("牛逼",2333);
            System.out.println(System.nanoTime()-start1);

            //20197073
            long start2 = System.nanoTime();
            IndexDao indexDao1 = (IndexDao) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{IndexDao.class}, new JdkInvocationHandler(new IndexDaoImpl()));
            indexDao1.test();
            indexDao1.test("2333");
            indexDao1.testReturn("牛逼",2333);
            System.out.println(System.nanoTime()-start2);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
