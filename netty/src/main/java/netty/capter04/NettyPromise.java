package netty.capter04;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * netty中的promise
 * 用来存储结果，可以主动向promise中添加结果，用于多个线程之间传递数据或者结果
 */
public class NettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventExecutors = new DefaultEventLoop();
        Promise<Integer> promise = new DefaultPromise<>(eventExecutors);
        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess(10);
            }catch (Exception e){
                promise.setFailure(e);
            }
        }).start();
        //从promise中获取结果
        //与netty中的future一样，支持阻塞同步获取结果、非阻塞同步获取结果（有可能为空）、通过监听器异步非阻塞获取结果
        System.out.println("等待结果。。。");
        System.out.println(promise.get());
    }
}
