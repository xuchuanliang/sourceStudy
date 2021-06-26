package com.ant.capter01;

import java.util.concurrent.TimeUnit;

/**
 * 通过手动创建一个10MB的对象，使用jmap工具查看堆内存变化
 * 1.使用jps查看进程id
 * 2.分别在三个时刻使用jmap -heap pid查看堆内存占用情况
 */
public class HeapTest2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("1.......");
        TimeUnit.SECONDS.sleep(20);
        byte[] bs = new byte[1024*1024*10];
        System.out.println("2.......");
        TimeUnit.SECONDS.sleep(20);
        bs = null;
        System.gc();
        System.out.println("3.......");
        TimeUnit.SECONDS.sleep(20);
    }
}
