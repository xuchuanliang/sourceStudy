package com.test1.ant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class AQS {
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        LOCK.lock();
        try {
            LOCK.lock();
            try {
                System.out.println("线程1加锁成功");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        } finally {
            LOCK.unlock();
        }
        Thread t2 = new Thread(() -> {
            LOCK.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });
    }
}
