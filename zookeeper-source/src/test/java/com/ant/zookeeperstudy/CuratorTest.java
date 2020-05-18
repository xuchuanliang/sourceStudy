package com.ant.zookeeperstudy;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CuratorTest {

    //    private static String connect = "192.168.109.130:2181,192.168.109.131:2181,192.168.109.132:2181";
    private static String connect = "192.168.162.139:2181,192.168.162.140:2181,192.168.162.141:2181,192.168.162.142:2181,192.168.162.143:2181,192.168.162.144:2181,192.168.162.145:2181";

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @Test
    public void test1() throws Exception {
        //构造client
        CuratorFramework client = CuratorFrameworkFactory.newClient(connect, 10000, 100000, new RetryOneTime(1000));
        client.start();
        InterProcessLock interProcessLock = new InterProcessMutex(client, "/lock");
        for(int i=0;i<10;i++){
            CompletableFuture.runAsync(()->{
                for(int j=0;j<100;j++){
                    try {
                        long s = System.nanoTime();
                        interProcessLock.acquire();
                        System.err.println(System.nanoTime()-s);
                        TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            interProcessLock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        TimeUnit.HOURS.sleep(1);
        client.close();
//        //开始连接
//        client.start();
//        String s = client.create().withMode(CreateMode.EPHEMERAL).forPath("/ant", "ant".getBytes());
//        System.out.println(s);
//        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
//            @Override
//            public void stateChanged(CuratorFramework client, ConnectionState newState) {
//            }
//        });
        Thread thread = new Thread();
        thread.start();
    }
}
