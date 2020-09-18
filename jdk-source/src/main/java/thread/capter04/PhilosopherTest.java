package thread.capter04;

import lombok.extern.slf4j.Slf4j;
import thread.Util;

/**
 * 哲学家死锁问题
 */
@Slf4j
public class PhilosopherTest {

    public static void main(String[] args) {
        //5支筷子
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");
        //5个哲学家
        Philosopher p1 = new Philosopher("勒布朗詹姆斯",c1,c2);
        Philosopher p2 = new Philosopher("德怀特安东尼",c2,c3);
        Philosopher p3 = new Philosopher("列夫托尔斯泰",c3,c4);
        Philosopher p4 = new Philosopher("科比布莱恩特",c4,c5);
        Philosopher p5 = new Philosopher("特雷西麦克格雷迪",c5,c1);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }

    static class Philosopher extends Thread{

        private Chopstick left;
        private Chopstick right;

        public Philosopher(String name, Chopstick left, Chopstick right) {
            super(name);
            this.left = left;
            this.right = right;
        }

        private void eat(){
            log.info("开始吃饭");
            Util.sleep(1);
        }

        @Override
        public void run() {
            synchronized (left){
                log.info("获取到左手的筷子");
                synchronized (right){
                    log.info("获取到右手的筷子");
                    eat();
                }
            }
        }
    }

    /**
     * 筷子
     */
    static class Chopstick{
        private String name;

        public Chopstick(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "筷子：{"+name+"}";
        }
    }
}
