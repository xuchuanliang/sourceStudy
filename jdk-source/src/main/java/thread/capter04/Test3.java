package thread.capter04;

/**
 * 互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
 * 同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点
 *
 * 本示例使用synchronized后的字节码如下；
 *          0: getstatic     #2                  // Field lock:Ljava/lang/Object;
 *          3: dup
 *          4: astore_1
 *          5: monitorenter
 *          6: getstatic     #3                  // Field count:I
 *          9: iconst_1
 *         10: iadd
 *         11: putstatic     #3                  // Field count:I
 *         14: aload_1
 *         15: monitorexit
 *         16: goto          24
 *         19: astore_2
 *         20: aload_1
 *         21: monitorexit
 *         22: aload_2
 *         23: athrow
 *         24: return
 */
public class Test3 {
    static int count = 0;
    static Object lock = new Object();

    public static void main(String[] args) {
        synchronized (lock){
            count++;
        }
    }
}
