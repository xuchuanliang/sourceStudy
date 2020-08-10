package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程八锁之一
 * a和b方法 synchronized 锁定的都是number实例，同一个实例，根据cpu调度情况有可能等待1秒后打印a b或打印b 等待一秒 a
 * c方法没有加锁，打印顺序可能是c 1s a b 或c b 1s a 或b c 1s a
 */
public class Test9 {

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
        Thread t3 = new Thread(()->{
            number.c();
        });
        t1.start();
        t2.start();
        t3.start();
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
        public void c(){
            log.error("c");
        }
    }
}
