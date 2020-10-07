package com.ant.capter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ThreadMethods {
    public static void main(String[] args) throws InterruptedException {
//        try {
//            test();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        testPark();
    }

    public static void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                //isInterrupted不会清除中断状态，也就是此处打印的状态是true
//                if (Thread.currentThread().isInterrupted()) {
//                    log.error("状态是：" + Thread.currentThread().isInterrupted());
//                    break;
//                }
                //interrupted会清除中断状态，也就是此处打印出来的状态是false
                if(Thread.interrupted()){
                    log.error("状态是：" + Thread.currentThread().isInterrupted());
                    break;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    log.error("睡眠中被打断，状态是：" + Thread.currentThread().isInterrupted());
                    //睡眠中被打断，打断状态会被清除，此时为了停止，我们手动设置打断标记，也就是此处打印出来的状态是false
                    Thread.currentThread().interrupt();
                }
            }
            log.error("退出循环");
        });
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        log.error("主线程开始打断thread线程,thread的状态是："+thread.getState());
        thread.interrupt();
    }

    public static void testPark() throws InterruptedException {
        Thread thread = new Thread(()->{
            log.error("park....");
            LockSupport.park();
            log.error("unpark....");
            log.error("打断状态："+Thread.currentThread().isInterrupted());
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        log.error("线程的状态是："+thread.getState());
        thread.interrupt();
//        LockSupport.unpark(thread);
    }

    public static void testPark2(){
        for(int i=0;i<5;i++){
            
        }
    }
}
