package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟多线程访问并修改共享资源产生的多线程问题
 * 针对共享变量，两个线程分别对他进行自增和自减操作
 */
@Slf4j
public class Test1 {
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {

        //共享变量
        Thread t1 = new Thread(()->{
            for(int i=0;i<5000;i++){
                count--;
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for(int i=0;i<5000;i++){
                count++;
            }
        },"t2");
        log.error("开始");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.error("结果是：{}",count);
    }
}
