package com.ant.capter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方式
 */
@Slf4j
public class CreateThreadWay {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
        test2();
        test3();
    }

    /**
     * 使用Thread创建和运行线程
     */
    public static void test1(){
        Thread t1 = new Thread(){
            @Override
            public void run() {
                log.error("直接使用Thread创建和运行线程");
            }
        };
        t1.start();
    }

    public static void test2(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.error("使用Runnable接口配合Thread创建和运行线程");
            }
        });
        t1.start();
    }

    public static void test3() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "使用Callalbe接口、FutureTask配合Thread实现创建和运行线程，【并且获取运行结果】";
            }
        });
        Thread t1 = new Thread(futureTask);
        t1.start();
        log.error(futureTask.get());
    }
}
