package com.ant.capter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
@Slf4j
public class ReentrantLockTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionOne = lock.newCondition();
    private static Condition conditionTwo = lock.newCondition();
    private volatile static boolean one = false;
    private volatile static boolean two = false;
    public static void main(String[] args) throws InterruptedException {
//        method1();
//        method3();
//        method4();
        method5();
    }

    public static void method1() {
        lock.lock();
        try {
            method2();
        } finally {
            lock.unlock();
        }
    }

    public static void method2() {
        lock.lock();
        try {
            log.error("reentrantLock 可重入");
        } finally {
            lock.unlock();
        }
    }

    public static void method3() {
        Thread t = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                lock.lockInterruptibly();
                try {
                    log.error("获取到了锁");
                } finally {
                    log.error("释放锁");
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                log.error("被打断了");
            }

        });
        t.start();
        lock.lock();
        log.error("主线程获取到了锁，此时t线程还在尝试获取锁，阻塞中....，我们尝试打断他");
        try {
            log.error("打断t获取锁的过程");
            t.interrupt();
        } finally {
            lock.unlock();
        }
    }

    public static void method4() throws InterruptedException {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                lock.lock();
                try{
                    log.error("获取到锁");
                }finally {
                    lock.unlock();
                }
            }
        },"t1").start();
        TimeUnit.MILLISECONDS.sleep(3);
        new Thread(()->{
            lock.lock();
            try{
                log.error("----->中间插入获得锁");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

    public static void method5(){
        new Thread(()->{
            lock.lock();
            try{
                while (!one){
                    //只要one 条件不满足，则进入one的waitSet等待
                    log.error("条件1不满足，进入one的waitset等待");
                    conditionOne.await();
                }
                log.error("条件1已经被满足，{}",one);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.error("释放锁");
                lock.unlock();
            }
        },"t1").start();
        new Thread(()->{
            lock.lock();
            try{
                while (!two){
                    //只要two 条件不满足，则进入one的waitSet等待
                    log.error("条件2不满足，进入two的waitset等待");
                    conditionTwo.await();
                }
                log.error("条件2已经被满足，{}",two);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.error("释放锁");
                lock.unlock();
            }
        },"t2").start();

        new Thread(()->{
            lock.lock();
            try{
                TimeUnit.MILLISECONDS.sleep(100);
                log.error("设置one为true，且唤醒在one的waitSet中等待的线程");
                one = true;
                conditionOne.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.error("释放锁");
                lock.unlock();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try{
                TimeUnit.MILLISECONDS.sleep(100);
                log.error("设置two为true，且唤醒在one的waitSet中等待的线程");
                two = true;
                conditionTwo.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.error("释放锁");
                lock.unlock();
            }
        },"唤醒线程").start();
    }
}
