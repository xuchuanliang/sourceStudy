package thread.sync;

public class Test {
    /**
     public synchronized void test1();
     descriptor: ()V
     flags: ACC_PUBLIC, ACC_SYNCHRONIZED   对于使用synchronizeds修饰的方法而言，会生成ACC_SYNCHRONIZED标识
     Code:
     stack=2, locals=1, args_size=1
     0: getstatic     #2                  // Field java/lang/System.err:Ljava/io/PrintStream;
     3: ldc           #3                  // String eee
     5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     8: return
     */
    public synchronized void test1(){
        System.err.println("eee");
    }

    /**
     public void test2();
     descriptor: ()V
     flags: ACC_PUBLIC
     Code:
     stack=2, locals=3, args_size=1
     0: aload_0
     1: dup
     2: astore_1
     3: monitorenter   进入monitor锁
     4: getstatic     #2                  // Field java/lang/System.err:Ljava/io/PrintStream;
     7: ldc           #3                  // String eee
     9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     12: aload_1
     13: monitorexit  退出monitor锁
     14: goto          22
     17: astore_2
     18: aload_1
     19: monitorexit  退出monitor锁
     20: aload_2
     21: athrow
     22: return
     */
    public void test2(){
        synchronized (this){
            System.err.println("eee");
        }
    }
}
