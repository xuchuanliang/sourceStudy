package thread.capter04;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟多线程访问并修改共享资源产生的多线程问题
 * 针对共享变量，两个线程分别对他进行自增和自减操作
 *
 * 使用javap命令查看该类生成的class对应的字节码信息，可以看到
 * 实际上i++在jvm中执行是分为4步执行：
 *  getstatic：读取静态变量的值
 *  iconst_1：准备常量1
 *  iadd：自增
 *  putstatic：将修改后的值存回到静态变量中
 *
 *  对应的i--操作在jvm执行也是分为4步：
 *  getstatic：读取静态变量的值
 *  iconst_1：准备常量1
 *  isub：自减
 *  putstatic：将修改后的值存回到静态变量中
 *
 *  在jmm中，执行自增或者自减操作实际上都需要java线程工作内存中的数据域主内存中的数据进行交换
 *  所以存在线程安全问题
 *  例如在t1线程进行iadd操作时发生了上下文切换，此时t2线程一次性执行完了i--操作，此时i是-1;但是当t1线程获取到时间片后，
 *  继续执行iadd以及putstatic字节码，那么此时就会将1覆盖至原来的静态变量，最终导致结果有误差
 *
 *
 *  临界区Critical Section：
 *  1.一个程序运行多个线程本身是没有问题的
 *  2.多个线程读共享资源其实也没有问题
 *  3.在多个线程对共享资源读写操作时发生指令交错，就会出现问题
 *  4.一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区
 *
 *  竟态条件Race Condition：
 *  多个线程在临界区内执行，由于代码的执行序列不同而导致结果无法预测，称之为发生了竞态条件
 *
 *  为了避免临界区的竞态条件发生，有多种手段可以达到目的
 *  阻塞式的解决方案：synchronized，Lock
 *  非阻塞式的解决方案：原子变量
 *
 *  互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
 *  同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点
 *
 *
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
