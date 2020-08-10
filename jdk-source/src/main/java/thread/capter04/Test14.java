package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁之一
 * synchronized  a方法和b方法锁定的都是类对象，不考虑类加载器的情况下，一个jvm中类对象只有一个
 * b 1s a 或1s a b
 */
public class Test14 {

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
        public static synchronized void a() throws InterruptedException {
            Thread.sleep(1000);
            log.error("a");
        }
        public static synchronized void b(){
            log.error("b");
        }
    }
}
