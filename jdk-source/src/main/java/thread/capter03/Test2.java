package thread.capter03;

/**
 * 线程的优先级：PRIORITY
 * 线程的优先级只是提示CPU有限调用优先级高的线程，但是具体的效果取决于CPU的实现
 * 也取决于CPU是否繁忙
 */
public class Test2 {
    public static void main(String[] args){
        Thread t1 = new Thread(()->{
            for(;;){
                System.out.println("---t1---");
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for(;;){
                System.out.println("+++t2+++");
            }
        },"t2");
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
    }
}
