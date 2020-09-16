package thread.capter05;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * wait notify
 * wait方法的作用是当前执行wait的线程进入持有的monitor锁的waitSet等待
 * 也就是调用wait之前必须持有锁，这是前提条件，即wait实际上是与synchronized内部锁同时出现的
 *
 * wait notify的正确姿势就是调用锁对象的wait和notify方法，因为java中每个对象都是与操作系统的一个monitor对象绑定的
 *
 * notify是唤醒当前线程持有的锁上的
 */
@Slf4j
public class WaitNotifyTest1 {
    static Object lock = new Object();
    @SneakyThrows
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                try {
                    log.error("t1进入wait");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("t1执行");
            }
        }).start();
        new Thread(()->{
            synchronized (lock){
                try {
                    log.error("t2进入wait");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("t2执行");
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        synchronized (lock){
            lock.notifyAll();
        }
    }
}
