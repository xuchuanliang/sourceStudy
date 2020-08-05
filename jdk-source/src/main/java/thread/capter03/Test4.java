package thread.capter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * interrupt
 * 打断线程执行
 * 如果当前线程是运行状态，那么调用该线程的interrupt方法之后，该线程的interrupt状态是true
 * 如果当前线程是阻塞状态，如wait，join，sleep，那么打断后该线程的interrupt状态是false，即会清空interrupt状态
 */
@Slf4j
public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true){
                boolean in = Thread.currentThread().isInterrupted();
                if(in){
                    log.error("被打断了，打断状态是：{}",in);
                    break;
                }
            }
        },"t1");

        log.error("main线程开始");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
    }
}
