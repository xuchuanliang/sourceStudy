package com.ant.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟两个线程对共享变量进行自增和自减操作，并从字节码层面解析原理
 */
@Slf4j
public class Counter {
    static int count = 0;
    private static Object room = new Object();

    public static void main(String[] args) throws Exception{
//        testNoSafeCount();
        testSafeCount1();
    }

    public static void testNoSafeCount() throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i=0;i<5000;i++){
                count++;
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for(int i=0;i<5000;i++){
                count--;
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.error("count的值是{}",count);
    }

    private static void testJavap(){
        count++;
    }

    public static void testSafeCount1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i=0;i<5000;i++){
                synchronized (room){
                    count++;
                }
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for(int i=0;i<5000;i++){
                synchronized (room){
                    count--;
                }
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.error("count的值是{}",count);
    }


}
