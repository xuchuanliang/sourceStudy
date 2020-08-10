package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁之一
 * synchronized 锁定的都是number实例，同一个实例，根据cpu调度情况有可能等待1秒后打印a b或打印b 等待一秒 a
 */
public class Test8 {

    public static void main(String[] args) {
        Number number = new Number();
        Thread t1 = new Thread(()->{
            try {
                number.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            number.b();
        });
        t1.start();
        t2.start();
    }

    @Slf4j
    private static class Number{
        public synchronized void a() throws InterruptedException {
            Thread.sleep(1000);
            log.error("a");
        }
        public synchronized void b(){
            log.error("b");
        }
    }
}
