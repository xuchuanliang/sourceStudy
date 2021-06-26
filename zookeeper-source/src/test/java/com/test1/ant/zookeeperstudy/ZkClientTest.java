package com.test1.ant.zookeeperstudy;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ZkClientTest {
//    private static String connect = "192.168.109.130:2181,192.168.109.131:2181,192.168.109.132:2181";
    private static String connect = "192.168.162.139:2181,192.168.162.140:2181,192.168.162.141:2181,192.168.162.142:2181,192.168.162.143:2181,192.168.162.144:2181,192.168.162.145:2181";

    private static AtomicInteger atomicInteger = new AtomicInteger();
    @Test
    public void test1() throws InterruptedException {
        ZkClient client = new ZkClient(connect);
        String ant = client.create("/ant", "ant", CreateMode.EPHEMERAL);
        client.subscribeDataChanges(ant, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("dataChange:" + dataPath + " " + data);
                atomicInteger.incrementAndGet();
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("数据被删除");
            }
        });
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void test2()throws Exception{
        ZkClient zkClient = new ZkClient(connect);
        for(int i=0;i<1000;i++){
            zkClient.writeData("/ant",i);
        }
        System.out.println(atomicInteger.get());
        TimeUnit.SECONDS.sleep(5);
        System.out.println(atomicInteger.get());
//        ZkClient client = new ZkClient(connect);
//        client.writeData("/ant","one");
////        System.out.println("saven");
////        TimeUnit.SECONDS.sleep(2);
//        client.writeData("/ant","two");
////        System.out.println("four");
////        TimeUnit.SECONDS.sleep(2);
//        client.writeData("/ant","three");
////        System.out.println("three");
////        TimeUnit.SECONDS.sleep(2);
//        client.delete("/ant");
//        TimeUnit.SECONDS.sleep(2);
    }
}
