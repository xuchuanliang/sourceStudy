package com.ant.zookeeperstudy;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class Test {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("10.100.33.103:2181",30000);
        zkClient.create("/xcl","xcl", CreateMode.PERSISTENT);
        System.out.println(zkClient.exists("/xcl"));
        zkClient.delete("/xcl");
    }
}
