package thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * join
 * 若A线程中需要等待B线程执行解决才能继续执行，那么就需要使用到join
 * 在A线程中调用B线程的join方法，就是等待B线程执行结束后A线程才继续执行
 * 若调用有时间限制的join方法，若时间到了，被调用线程仍未执行结束，那么调用方也会继续执行而不再等待被调用方执行结束
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                log.warn("t1 start");
                TimeUnit.SECONDS.sleep(1);
                log.warn("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        log.warn("main thread start");
        t1.start();
        t1.join(500);
        log.warn("end join,main continue");
    }
}
