package thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 主线程与守护线程
 * 一般情况下jvm会等待所有的主线程执行完毕才会退出jvm虚拟机
 * 当所有的主线程执行完毕后，即使还存在正在运行的守护线程，jvm仍然会退出jvm虚拟机
    设置是否是守护线程，使用java.lang.Thread#setDaemon(boolean)设置
 */
@Slf4j
public class Test6 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i=0;i<15;i++){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("t1线程执行中");
            }
            log.error("退出t1线程");
        },"t1");
        Thread t2 = new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("守护线程执行中");
            }
        },"t2");
        t2.setDaemon(true);
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(10);
        log.error("退出主线程");
    }
}
