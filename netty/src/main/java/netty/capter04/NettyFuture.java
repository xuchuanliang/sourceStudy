package netty.capter04;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * netty中future演示
 * 除了包含jdk中的future功能之外，还包含了非阻塞获取结果，通过添加回调方法实现异步获取结果
 */
public class NettyFuture {
    private static Logger logger = LoggerFactory.getLogger(NettyFuture.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventExecutors = new DefaultEventLoop();
        Future<Integer> future = eventExecutors.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //同步阻塞获取结果
//        future.get();
        //时间限制内获取结果
//        future.get(1,TimeUnit.SECONDS);
        // 同步非阻塞获取结果，，如果还没结果则直接返回空
//        future.getNow();
        //异步非阻塞获取结果，通过回调函数
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                logger.debug(future.getNow().toString());
            }
        });
    }
}
