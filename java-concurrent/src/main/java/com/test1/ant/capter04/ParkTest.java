package com.test1.ant.capter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParkTest {
    public static void main(String[] args) throws InterruptedException {
//        parkUnPark();
        unParkPark();
    }

    /**
     * park 和unPark都是以线程为单位，不需要monitor锁的配合
     *  park会中断当前运行的线程，unPark会唤醒被中断的线程
     * @throws InterruptedException
     */
    public static void parkUnPark() throws InterruptedException {
        Thread t = new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.error("开始park");
            LockSupport.park();
            log.error("继续运行");
        },"t1");
        t.start();
        TimeUnit.SECONDS.sleep(1);
        log.error("开始unPark");
        LockSupport.unpark(t);
        log.error("unPark结束");
    }

    /**
     * unPark  park配合有点类似于在Thread中有一个int属性i，最大为2，当调用一次unPark就会将i值+1
     * 当调用park时会将i值-1，且会判断，如果此时i值为0，则会中断当前线程；
     * 默认初始值为1，当先调用unPark时，i值变成了2，此时再调用park则不会中断线程，但是多次重复调用unPark只会最多将i值增加到2
     * @throws InterruptedException
     */
    public static void unParkPark() throws InterruptedException {
        Thread t = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.error("开始执行park");
            LockSupport.park();
            log.error("第一次park结束");
            log.error("第二次park开始");
            LockSupport.park();
            log.error("第二次park结束，会继续中断，可以体现出调用多次unPark只有一次作用");
        },"t1");
        t.start();
        TimeUnit.MILLISECONDS.sleep(500);
        log.error("先执行unPark");
        LockSupport.unpark(t);
        log.error("unPark结束");
        log.error("再执行一次unPark");
        LockSupport.unpark(t);
    }
}
