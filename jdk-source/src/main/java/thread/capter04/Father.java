package thread.capter04;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Father {
    private List<String> list = new Vector<>(500000);

    public void add(String i) {
        list.add(i);
    }

    public void remove(String i) {
        while (!list.remove(i)){

        }
    }

    public void over() {
        for (int i = 0; i < 5000; i++) {
            add(i + "");
            remove(i + "");
        }
    }

    public List<String> getList() {
        return list;
    }

    public static void main(String[] args) throws InterruptedException {
        Father father = new Father();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(20));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                father.add(i + "");
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(20));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                father.remove(i + "");
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(father.getList().size());
        System.out.println("end");
    }
}
