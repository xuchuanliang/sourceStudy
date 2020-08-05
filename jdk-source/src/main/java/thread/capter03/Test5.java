package thread.capter03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 设计模式之两阶段终止，即通过interrupt优雅停止线程
 */
@Slf4j
public class Test5 {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();
        TimeUnit.SECONDS.sleep(5);
        log.error("开始打断监视器");
        monitor.stop();
    }
}

/**
 * 两阶段优雅终止线程
 * 模拟监控系统，每1秒钟执行一次监控
 * 当用户停止监控之后，需要关闭指定资源再停止监控
 */
@Slf4j
class Monitor{
    private Thread monitor = null;

    public Monitor() {
        monitor = new Thread(()->{
           while (true){
               //每隔一秒钟执行一次监控
               if(Thread.currentThread().isInterrupted()){
                   //被打断，回收资源，然后跳出循环
                   log.error("已经被停止，开始回收资源。。。");
                   break;
               }
               try {
                   TimeUnit.SECONDS.sleep(1);
                   log.error("监控一次数据");
               } catch (InterruptedException e) {
                    //当在睡眠状态被打断时，他的interrupt状态会被重置
                   //那么这个案例中会出现在sleep中被打断，被打断后由于会清空线程的打断状态
                   //所以会出现即使主线程将监控线程打算，但是监视线程的打断状态被清空，导致无法停止，
                   //因此此处应当对在睡眠状态下被打断的情况进行处理
                   Thread.currentThread().interrupt();
               }
           }
        });
    }

    public void start(){
        monitor.start();
    }

    public void stop(){
        //优雅终止，运维监控线程处理后事
        monitor.interrupt();
    }
}

