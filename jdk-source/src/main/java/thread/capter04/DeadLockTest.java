package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

/**
 * 死锁
 * 使用jconsole或jstack定位死锁
 * 解决死锁可以按照顺序获取锁
 * 另外如果由于某个线程进入了死循环，导致其它线程一直等待，对于这种情况 linux 下可以通过 top 先定位到CPU 占用高的 Java 进程，再利用 top -Hp 进程id 来定位是哪个线程，最后再用 jstack 排查
 */
@Slf4j
public class DeadLockTest {

    private static final Object l1 = new Object();
    private static final Object l2 = new Object();

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        new Thread(()->{
            synchronized (l1){
                log.info("l1加锁成功");
                Util.sleep(1);
                synchronized (l2){
                    log.info("l2加锁成功");
                }
            }
        }).start();
        new Thread(()->{
            synchronized (l2){
                log.info("l2加锁成功");
                Util.sleep(1);
                synchronized (l1){
                    log.info("l1加锁成功");
                }
            }
        }).start();
    }
}
