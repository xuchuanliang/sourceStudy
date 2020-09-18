package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

import java.util.concurrent.TimeUnit;

/**
 * 活锁问题
 * 活锁出现在两个线程互相改变对方的结束条件，最后谁也无法结束
 */
@Slf4j
public class TestLiveLock {
    private static final Object lock = new Object();
    private static int count = 10;
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            while (count > 0){
                count--;
                Util.sleep(100, TimeUnit.MILLISECONDS);
                log.info("{}",count);
            }
            log.info("t1结束");
        },"t1");
        Thread t2 = new Thread(()->{
            while (count < 20){
                count++;
                Util.sleep(100,TimeUnit.MILLISECONDS);
                log.info("{}",count);
            }
            log.info("t2结束");
        },"t2");
        t1.start();
        t2.start();
    }
}
