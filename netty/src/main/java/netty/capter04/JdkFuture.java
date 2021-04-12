package netty.capter04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * jdk中的future演示
 * 表示某个线程执行计算的结果，可以通过get方法等待线程执行结果，get会阻塞
 */
public class JdkFuture {
    private static Logger log = LoggerFactory.getLogger(JdkFuture.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> f1 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("calculate。。。");
            return 10;
        });
        Future<Integer> f2 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("calculate。。。");
            return 20;
        });
        Future<Integer> f3 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("calculate。。。");
            return 30;
        });
        Future<Integer> f4 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("calculate。。。");
            return 40;
        });
        log.debug(f1.get().toString());
        log.debug(f2.get().toString());
        log.debug(f3.get().toString());
        log.debug(f4.get().toString());
    }
}
