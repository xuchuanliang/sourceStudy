package netty.capter02;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 测试EventLoopGroup以及EventLoop相关
 */
public class TestEventLoopGroup {
    private static Logger log = LoggerFactory.getLogger(TestEventLoopGroup.class);

    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(2);//处理IO事件、普通任务、定时任务
//        EventLoopGroup eventExecutors1 = new DefaultEventLoopGroup();//普通任务、定时任务
//        System.setProperty("io.netty.eventLoopThreads","8");
//        int threadNum = Math.max(1, SystemPropertyUtil.getInt(
//                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
//        System.out.println(threadNum);
        //线程组里面的线程轮询处理任务
//      log.debug(eventExecutors.next().toString());
//      log.debug(eventExecutors.next().toString());
//      log.debug(eventExecutors.next().toString());
//      log.debug(eventExecutors.next().toString());
        //使用EventLoopGroup提交任务
//        eventExecutors.next().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.debug("ok");
//        });
//        eventExecutors.next().execute(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.debug("ok");
//        });
        //使用EventLoopGroup提交定时任务
        eventExecutors.next().scheduleAtFixedRate(()->log.debug("ok"),0,1,TimeUnit.SECONDS);
        log.debug("main");
    }
}
