package thread.capter04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.Util;

import java.util.concurrent.TimeUnit;

/**
 * wait和notify
 */
public class WaitNotifyTest {
    private static Logger log = LoggerFactory.getLogger(WaitNotifyTest.class);
    private static final Object LOCK = new Object();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     * 使用sleep实现，但是sleep期间线程会一直持有对象锁，并且无法判断达到条件的时间
     * 其它干活的线程，都要一直阻塞，效率太低
     * 小南线程必须睡足 2s 后才能醒来，就算烟提前送到，也无法立刻醒来
     * 加了 synchronized (room) 后，就好比小南在里面反锁了门睡觉，烟根本没法送进门，main 没加synchronized 就好像 main 线程是翻窗户进来的
     *解决方法，使用 wait - notify 机制
     */
    public static void test1(){
        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.error("有烟没{}",hasCigarette);
                if(!hasCigarette){
                    log.debug("没有烟，再等一下");
                    Util.sleep(2, TimeUnit.SECONDS);
                }
                log.debug("有烟没{}",hasCigarette);
                if(hasCigarette){
                    log.debug("开始工作了");
                }
            }
        },"小红");

        Thread t2 = new Thread(()->{
            synchronized (LOCK){
                log.debug("有外卖没{}",hasTakeout);
                if(!hasTakeout){
                    log.debug("没有外卖，再等一下");
                    Util.sleep(2, TimeUnit.SECONDS);
                }
                log.debug("有外卖没{}",hasTakeout);
                if(hasTakeout){
                    log.debug("开始工作了");
                }
            }
        },"小蓝");
        t1.start();
        t2.start();
        Util.sleep(1,TimeUnit.SECONDS);
        new Thread(()->{
            log.debug("烟到了");
            hasCigarette = true;
        },"送烟大侠").start();

    }

    /**
     * 使用notify和wait机制实现
     * 解决了其它干活的线程阻塞的问题
     * 但如果有其它线程也在等待条件呢？
     */
    public static void test2(){
        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.info("有烟没{}",hasCigarette);
                if(!hasCigarette){
                    log.info("没有烟");
                    Util.waitTime(LOCK,2,TimeUnit.SECONDS);
                }
                log.info("有烟没{}",hasCigarette);
                if(hasCigarette){
                    log.info("有烟了，开始工作");
                }
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (LOCK){
                log.info("开始工作");
            }
        },"小女");
        t1.start();
        t2.start();
        Util.sleep(1);
        new Thread(()->{
            synchronized (LOCK){
                log.info("烟到了");
                hasCigarette=true;
                LOCK.notify();
            }
        }).start();
    }

    /**
     * notify 只能随机唤醒一个 WaitSet 中的线程，这时如果有其它线程也在等待，那么就可能唤醒不了正确的线
     * 程，称之为【虚假唤醒】
     * 这种情况我们称为虚假唤醒
     * 我们可以用notifyAll()来实现
     */
    public static void test3(){
        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.info("有烟没{}",hasCigarette);
                if(!hasCigarette){
                    log.info("没有烟");
                    Util.waitTime(LOCK,2,TimeUnit.SECONDS);
                }
                log.info("有烟没{}",hasCigarette);
                if(hasCigarette){
                    log.info("有烟了，开始工作");
                }else{
                    log.info("没有烟，结束工作");
                }
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了没{}",hasTakeout);
                if(!hasTakeout){
                    log.info("外卖还没到");
                    Util.waitTime(LOCK,2);
                }
                log.info("外卖到了没{}",hasTakeout);
                if(hasTakeout){
                    log.info("有外卖了，开始工作");
                }else{
                    log.info("没等待外卖，结束工作");
                }
                log.info("开始工作");
            }
        },"小女");
        t1.start();
        t2.start();
        Util.sleep(1);
        new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了");
                hasTakeout=true;
                LOCK.notify();
            }
        }).start();
    }

    /**
     * notifyAll
     * 用 notifyAll 仅解决某个线程的唤醒问题，但使用 if + wait 判断仅有一次机会，一旦条件不成立，就没有重新
     * 判断的机会了
     * 解决方法，用 while + wait，当条件不成立，再次 wait
     */
    public static void test4(){
        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.info("有烟没{}",hasCigarette);
                if(!hasCigarette){
                    log.info("没有烟");
                    Util.waitTime(LOCK,2,TimeUnit.SECONDS);
                }
                log.info("有烟没{}",hasCigarette);
                if(hasCigarette){
                    log.info("有烟了，开始工作");
                }else{
                    log.info("没有烟，结束工作");
                }
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了没{}",hasTakeout);
                if(!hasTakeout){
                    log.info("外卖还没到");
                    Util.waitTime(LOCK,2);
                }
                log.info("外卖到了没{}",hasTakeout);
                if(hasTakeout){
                    log.info("有外卖了，开始工作");
                }else{
                    log.info("没等待外卖，结束工作");
                }
                log.info("开始工作");
            }
        },"小女");
        t1.start();
        t2.start();
        Util.sleep(1);
        new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了");
                hasTakeout=true;
                LOCK.notifyAll();
            }
        }).start();
    }

    /**
     * notify的正确用法是使用while循环来实现
     *
     */
    public static void test5(){
        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.info("有烟没{}",hasCigarette);
                while (!hasCigarette){
                    log.info("烟还没到");
                    Util.waitTime(LOCK,2);
                }
                log.info("有烟了，开始工作");
            }
        },"小南");
        Thread t2 = new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了没{}",hasTakeout);
                while (!hasTakeout){
                    log.info("外卖还没到");
                    Util.waitTime(LOCK,2);
                }
                log.info("外卖到了，开始工作");
            }
        },"小女");
        t1.start();
        t2.start();
        Util.sleep(1);
        new Thread(()->{
            synchronized (LOCK){
                log.info("外卖到了");
                hasTakeout=true;
                LOCK.notifyAll();
            }
        }).start();
        Util.sleep(500,TimeUnit.MILLISECONDS);
        new Thread(()->{
           synchronized (LOCK){
               log.info("烟到了");
               hasCigarette = true;
               LOCK.notifyAll();
           }
        }).start();
    }

}
