package com.deepJVM.capter04;

import java.util.concurrent.TimeUnit;

public class JStackTest {
    private static Object obj1 = new Object();
    private static Object obj2 = new Object();
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        createBusyThread();
        createLockThread(new Object());
        createDeadLock();
    }

    /**
     * 死循环
     */
    public static void createBusyThread(){
        new Thread(()->{
            while (true){

            }
        },"busyThread").start();
    }
    /**
     * 线程锁等待演示
     */
    public static void createLockThread(final Object lock){
        new Thread(()->{
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"testLockThread").start();
    }

    public static void createDeadLock() throws InterruptedException {
        new Thread(()->{
            synchronized (obj1){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2){
                    System.out.println("ok");
                }
            }
        },"dead1").start();
        new Thread(()->{
            synchronized (obj2){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1){
                    System.out.println("ok");
                }
            }
        },"dead2").start();
    }

}
