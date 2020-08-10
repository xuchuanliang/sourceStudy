package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁之一
 * synchronized锁的是实例对象，但是分别两个实例，也就是两把锁
 * b 1s a
 */
public class Test10 {

    public static void main(String[] args) {
        Number number1 = new Number();
        Number number2 = new Number();
        Thread t1 = new Thread(()->{
            try {
                number1.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            number2.b();
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
