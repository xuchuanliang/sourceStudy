package thread.capter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 使用多线程模拟 华罗庚《统筹方法》多线程烧茶泡茶，达到耗时最少
 * 洗水壶 1分钟 烧开水 15分钟
 * 洗茶壶 1分钟
 * 洗茶杯 2分钟
 * 拿茶叶 1分钟
 */
@Slf4j
public class Test8 {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                log.error("洗水壶");
                TimeUnit.SECONDS.sleep(1);
                log.error("烧开水");
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "小男");
        Thread t2 = new Thread(()->{
            try {
                log.error("洗茶壶");
                TimeUnit.SECONDS.sleep(1);
                log.error("洗茶叶");
                TimeUnit.SECONDS.sleep(2);
                log.error("拿茶叶");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"小女");

        log.error("主人开始派任务");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.error("所有的准备工作已经完成，开始泡茶喝茶啦。。");
    }

}
