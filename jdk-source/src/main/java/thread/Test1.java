package thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 线程创建的三种方式：
 * 第二种使用Runnable的方式更加灵活，这种方式将线程与任务进行解耦，能够组合出来更高级的api
 *
 *
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("创建线程的方式1：{}","通过重写Thread的run方法构造线程");
            }
        };


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("创建线程的方式2：{}","通过构造Runnable的匿名内部类，将Runnable线程传递给Thread构造线程");
            }
        },"t2");

        Thread t3 = new Thread(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("创建线程的方式3：{}","能够获取结果的方式，使用FutureTask，Future继承了Runnable，实际上创建方式与方式2类似，" +
                        "只不过此种方式可以通过FutureTask+Callable的组合实现获取线程的执行结果");
                return "ss";
            }
        }),"t3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        log.error("执行完成");
    }
}
