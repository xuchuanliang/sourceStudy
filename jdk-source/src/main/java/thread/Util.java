package thread;

import java.util.concurrent.TimeUnit;

public final class Util {
    public static void sleep(long time, TimeUnit timeUnit){
        try {
            Thread.sleep(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(long time){
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void waitTime(Object lock,long time,TimeUnit timeUnit){
        try {
            lock.wait(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitTime(Object lock,long time){
        try {
            lock.wait(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
