package thread.capter04;

/**
 * 线程八锁之一
 * synchronized 锁定的都是number实例，同一个实例，根据cpu调度情况有可能打印a b或打印b a
 */
public class Test7 {

    public static void main(String[] args) {
        Number number = new Number();
        Thread t1 = new Thread(()->{
            number.a();
        });
        Thread t2 = new Thread(()->{
            number.b();
        });
        t1.start();
        t2.start();
    }

    private static class Number{
        public synchronized void a(){
            System.out.println("a");
        }
        public synchronized void b(){
            System.out.println("b");
        }
    }
}
