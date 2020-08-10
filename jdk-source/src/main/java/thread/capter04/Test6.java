package thread.capter04;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;

public class Test6 {

    @JunitPerfConfig(threads = 3,warmUp = 1000,duration = 5000)
    public void junitPerfConfigTest() throws InterruptedException {
        System.out.println("junitPerfConfigTest");
        Thread.sleep(100);
    }
}