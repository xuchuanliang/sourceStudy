package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁之一
 * synchronized  锁定的都是类对象，是同一个对象
 * 1s a b  或 b 1s a
 */
public class Test12 {

    public static void main(String[] args) {
        Number number1 = new Number();
        Thread t1 = new Thread(()->{
            try {
                number1.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            number1.b();
        });
        t1.start();
        t2.start();
    }

    @Slf4j
    private static class Number{
        public static synchronized void a() throws InterruptedException {
            Thread.sleep(1000);
            log.error("a");
        }
        public static synchronized void b(){
            log.error("b");
        }
    }
}
