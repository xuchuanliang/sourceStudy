package caseNetty.capter01;

import java.util.concurrent.TimeUnit;

/**
 * JVM守护线程
 */
public class TestDaemon {
    public static void main(String[] args) throws InterruptedException {
//        daemon();
        unDaemon();
    }

    private static void daemon() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("守护线程状态下，进程结束");
    }

    public static void unDaemon() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(false);
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("守护线程状态下，进程结束");
    }
}
