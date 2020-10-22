package com.ant.capter04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class WaitTest {
    public static void main(String[] args) throws InterruptedException {
//        status();
        useful();
    }

    public static void status() throws InterruptedException {
        Thread thread = new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        });
        thread.start();
        Thread.sleep(200);
        System.out.println(thread.getState());
    }

    private static boolean conditionTrue = Boolean.FALSE;
    public static void useful(){
        Object lock = new Object();
        Thread t1 = new Thread(()->{
            synchronized (lock){
                //当前已经持有了锁
                //判断条件是否成立，
                //如果条件成立则执行正常的业务逻辑；如果条件不成立则继续等待
                //因为在monitor等待的线程都在wait set中等待，唤醒也是随机唤醒，所以即使被唤醒也无法确定是否是当前线程的条件成立，所以使用while(条件不成立){wait()}的方式
                //说人话就是即使被唤醒，如果条件不成立，则继续等待
                //所以wait/notify/notifyAll都必须要在获取到对象锁的情况下使用
                while (!conditionTrue){
                    //被虚假唤醒，实际上条件未成立，则继续等待
                }
                log.error("条件成立，则执行业务逻辑");
            }
        });
        t1.start();
        Thread t2 = new Thread(()->{
            synchronized (lock){
                //获取锁，等待一秒后，模拟条件成立
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                conditionTrue=true;
                //将条件设置成立之后，则唤醒在lock 的monitor中等待的所有线程
                lock.notifyAll();
            }
        });
        t2.start();
    }
}
