package caseNetty.capter01;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        //通过给Runtime添加钩子监听系统退出
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("ShutdownHook execute start....");
            System.out.println("Netty NioEventLoopGroup shutdownGracefully....");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ShutdownHook execute end....");
        }));
        TimeUnit.SECONDS.sleep(7);
        System.exit(0);

        //通过监听信号量来监听系统退出
        //1.获取系统对应的信号名称
        String signal = System.getProperties().getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "TERM";
        Signal sig = new Signal(signal);
        Signal.handle(sig, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                //针对不同的信号量做出不同的反应
            }
        });
    }
}
