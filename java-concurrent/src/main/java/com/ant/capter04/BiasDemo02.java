package com.ant.capter04;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 使用jol 查看对象头
 * -XX:BiasedLockingStartupDelay=0
 */
@Slf4j
public class BiasDemo02 {
    public static void main(String[] args) {
        Object obj = new Object();
        new Thread(()->{
            log.debug("加锁前头信息");
            log.error(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj){
                log.error("已经加锁后的头信息");
                log.error(ClassLayout.parseInstance(obj).toPrintable());
            }
            log.error("加锁结束后的头信息");
            log.error(ClassLayout.parseInstance(obj).toPrintable());
        },"t1").start();
    }
}
