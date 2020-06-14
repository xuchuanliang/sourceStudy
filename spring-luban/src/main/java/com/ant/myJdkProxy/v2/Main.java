package com.ant.myJdkProxy.v2;

/**
 * 测试主函数
 */
public class Main {
    public static void main(String[] args){
        IndexDao indexDao = new IndexDaoImpl();
        try {
            IndexDao proxy = (IndexDao) ProxyUtil.getProxy(indexDao, IndexDao.class, new IndexInvocationHandler() );
            proxy.test();
            proxy.test("2333");
            proxy.testReturn("牛逼",2333);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
