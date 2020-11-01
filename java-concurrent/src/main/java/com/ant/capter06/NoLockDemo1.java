package com.ant.capter06;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 无锁
 */
@Slf4j
public class NoLockDemo1 {

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<5;i++){
            testThreadUnSafe(new AccountCAS("ant",10000));
        }
    }

    public static void testThreadUnSafe(AbstractAccount account) throws InterruptedException {
        long start = System.nanoTime();
        List<Thread> threadList = new ArrayList<>(1000);
        for(int i=0;i<1000;i++){
            threadList.add(new Thread(()->{
                for(int j=0;j<10;j++){
                    account.descBalance(1);
                }
            }));
        }
        threadList.forEach(Thread::start);
        //等待所有扣减余额结束
        for (Thread thread : threadList) {
            thread.join();
        }
        //获取当前余额
        log.error("账户信息是：{},-------耗费时间是：{}",account,(System.nanoTime()-start));
    }
}
