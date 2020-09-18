package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示锁
 * 相比于sync内部锁，他的优点比较明显：
 * 可中断
 * 可以设置超时时间
 * 可以设置为公平锁
 * 支持多个条件变量
 */
@Slf4j
public class ReentrantLockTest {
    private static final java.util.concurrent.locks.ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 可重入
     */
    public static void test1() {
        REENTRANT_LOCK.lock();
        try {
            log.info("test()已经加锁");
            lockAgain();
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

    public static void lockAgain() {
        REENTRANT_LOCK.lock();
        try {
            log.info("method()加锁成功");
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

    /**
     * 可打断，在获取锁的过程中可以被打断
     */
    public static void test2() {
        Thread t = new Thread(() -> {
            try {
                log.error("获取锁");
                REENTRANT_LOCK.lockInterruptibly();
            } catch (InterruptedException e) {
                log.error("被打断");
                e.printStackTrace();
            }
            try {
                Util.sleep(2);
                log.error("执行完成业务逻辑");
            } finally {
                REENTRANT_LOCK.unlock();
            }
        });
        //主线程先使用ReentrantLock加锁，然后尝试中断正在尝试获取锁的线程，看看能否中断
        REENTRANT_LOCK.lock();
        try {
            log.error("启动t线程，使用lockInterrupibly尝试加锁");
            t.start();
            Util.sleep(1);
            log.error("尝试中断锁");
            t.interrupt();
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

    /**
     * ReentrantLock 直接加锁，测试在加锁过程中能否被打断
     */
    public static void test3() {
        Thread t = new Thread(() -> {
            log.error("开始尝试加锁");
            REENTRANT_LOCK.lock();
            try {
                log.error("加锁成功，执行业务逻辑");
            } finally {
                log.error("解锁");
                REENTRANT_LOCK.unlock();
            }
        });

        log.error("主线程先加锁");
        REENTRANT_LOCK.lock();
        t.start();
        try {
            Util.sleep(1);
            log.error("尝试打断t线程加锁过程");
            t.interrupt();
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

    /**
     * 锁超时
     */
    public static void test4(){
        Thread t = new Thread(()->{
            log.error("尝试加锁");
            if(!REENTRANT_LOCK.tryLock()){
                log.error("加锁失败，流程结束");
                return;
            }
            try {
                log.error("加锁成功,执行业务逻辑");
                Util.sleep(1);
            }finally {
                log.error("解锁失败");
                REENTRANT_LOCK.unlock();
            }
        });
        log.error("主线程先加锁");
        REENTRANT_LOCK.lock();
        try {
            Util.sleep(2);
            t.start();
        }finally {
            REENTRANT_LOCK.unlock();
        }
    }
}
