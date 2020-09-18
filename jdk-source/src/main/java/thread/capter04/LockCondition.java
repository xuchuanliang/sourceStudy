package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

import java.util.concurrent.TimeUnit;

/**
 *一间大屋子有两个功能：睡觉、学习，互不相干。  即两个业务功能不会修改同一处共享资源
 * 现在小南要学习，小女要睡觉，但如果只用一间屋子（一个对象锁）的话，那么并发度很低
 * 解决方法是准备多个房间（多个对象锁）
 *
 * 好处，是可以增强并发度
 * 坏处，如果一个线程需要同时获得多把锁，就容易发生死锁
 */
@Slf4j
public class LockCondition {
    private static final Object ROOM = new Object();
    private static final Object STUDY_ROOM = new Object();
    private static final Object SLEEP_ROOM = new Object();
    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 下面这种情况，当两个线程逻辑对共享资源的访问是完全没关系的，那么使用同一把锁会导致并发性会极大的降低
     * 这种情况建议降低锁的粒度，降低锁的争用
     */
    public static void test1(){
        Thread t1 = new Thread(()->{
            synchronized (ROOM){
                log.info("学习一个小时");
                Util.sleep(1);
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (ROOM){
                log.info("睡觉8个小时");
                Util.sleep(8);
            }
        },"小女");
        t1.start();
        t2.start();
        Util.join(t1);
        Util.join(t2);
        log.info("结束");
    }

    /**
     * 通过降低锁的粒度，减少锁的争用
     */
    public static void test2(){
        Thread t1 = new Thread(()->{
            synchronized (STUDY_ROOM){
                log.info("学习一个小时");
                Util.sleep(1);
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (SLEEP_ROOM){
                log.info("睡觉8个小时");
                Util.sleep(8);
            }
        },"小女");
        t1.start();
        t2.start();
        Util.join(t1);
        Util.join(t2);
        log.info("结束");
    }
}
