package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

import java.util.concurrent.locks.LockSupport;

/**
 * Park UnPark测试
 * park 和unpark与 Object 的 wait & notify 相比
 * wait，notify 和 notifyAll 必须配合 Object Monitor 一起使用，而 park，unpark 不必
 * park & unpark 是以线程为单位来【阻塞】和【唤醒】线程，而 notify 只能随机唤醒一个等待线程，notifyAll是唤醒所有等待线程，就不那么【精确】
 * park & unpark 可以先 unpark，而 wait & notify 不能先 notify
 */
@Slf4j
public class ParkTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * LockSupport.park() 的意思是暂停当前现场，跟进去可以看到是调用UNSAFE的park()方法
     * LockSupport.park() 的意思是恢复某个线程的运行，跟进去可以看到是调用UNSAFE的unpark()方法
     * 先调用park() 再调用unpark()
     */
    public static void test1() {
        Thread t = new Thread(() -> {
            log.info("调用park方法");
            LockSupport.park();
            log.info("继续执行");
        }, "ant");
        t.start();
        Util.sleep(1);
        log.info("调用unpark方法");
        LockSupport.unpark(t);
    }

    /**
     * 先调用unpark()方法，再调用park方法
     */
    public static void test2() {
        Thread t = new Thread(() -> {
            Util.sleep(2);
            log.info("调用park方法");
            LockSupport.park();
            log.info("继续执行");
        });
        t.start();
        Util.sleep(1);
        log.info("调用unpark()方法");
        LockSupport.unpark(t);
        log.info("main线程调用unpark结束");
    }
}
