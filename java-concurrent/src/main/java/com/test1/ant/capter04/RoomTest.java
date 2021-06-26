package com.test1.ant.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用面向对象的方式实现synchronize内部锁
 * 实现线程安全的将数据自增以及自减各5000次
 */
@Slf4j
public class RoomTest {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(()->{
            for(int i=0;i<5000;i++){
                room.increment();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<5000;i++){
                room.decrement();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.error("现在count的大小是{}",room.get());
    }
}
