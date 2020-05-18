package com.ant.zookeeperstudy;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperStudyApplicationTests {

//    private static String connect = "192.168.109.130:2181,192.168.109.131:2181,192.168.109.132:2181";
    private static String connect = "192.168.162.139:2181,192.168.162.140:2181,192.168.162.141:2181,192.168.162.142:2181,192.168.162.143:2181,192.168.162.144:2181,192.168.162.145:2181";

    @Test
    public void test1() throws IOException, InterruptedException, KeeperException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connect, 30000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                if(state == Event.KeeperState.SyncConnected){
                    System.out.println("连接成功");
                    countDownLatch.countDown();
                }
                if(type == Event.EventType.NodeCreated){
                    System.out.println("创建节点："+watchedEvent.getPath());
                }
                if(type == Event.EventType.NodeDeleted){
                    System.out.println("节点删除："+watchedEvent.getPath());
                };
                if(type == Event.EventType.NodeDataChanged){
                    System.out.println("节点数据改变："+watchedEvent.getPath());
                }
            }
        });
        countDownLatch.await();
        String path = zooKeeper.create("/ant", "ant".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        zooKeeper.getChildren(path,true);
        TimeUnit.HOURS.sleep(1);
    }

}
