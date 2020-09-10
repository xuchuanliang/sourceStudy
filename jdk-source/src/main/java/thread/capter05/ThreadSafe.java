package thread.capter05;

import java.util.ArrayList;
import java.util.List;

public class ThreadSafe {
    private List<String> list = new ArrayList<>();
    public void method1(int loopNumber){
        for(int i=0;i<loopNumber;i++){
            method2();
            method3();
        }
    }

    public void method2(){
        list.add("1");
    }

    public void method3(){
        list.remove(0);
    }

    static final int THREAD_NUM = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadSafe threadSafe = new ThreadSafe();
        for(int i=0;i<THREAD_NUM;i++){
            new Thread(()->{
                threadSafe.method1(LOOP_NUMBER);
            }).start();
        }
    }
}
