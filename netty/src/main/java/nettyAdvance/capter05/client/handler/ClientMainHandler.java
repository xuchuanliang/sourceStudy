package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nettyAdvance.capter05.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 客户端主线程处理器，主要不断阻塞监听用户输入消息，发送
 */
public class ClientMainHandler extends ChannelInboundHandlerAdapter {

    private static Logger log = LoggerFactory.getLogger(ClientMainHandler.class);

    private static CountDownLatch COUNT_DOWN = new CountDownLatch(1);
    private static AtomicBoolean LOGIN_FLAG = new AtomicBoolean(false);
    //客户端在与服务器端建立完成会触发active事件，负责向服务器发送各种消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //为了不阻塞netty的nio线程，创建新的线程负责阻塞从控制台读取消息，向服务器发送各种消息
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入用户名：");
            String username = scanner.nextLine();
            System.out.println("请输入密码：");
            String password = scanner.nextLine();
            //构造登陆请求对象
            Message message = new LoginRequestMessage(username,password);
            //发送登陆请求
            //此时会触发出栈操作，先通过MessageCodec对Message进行编码，然后通过LoggingHandler进行日志记录，最终从客户端写出到服务器端
            ctx.writeAndFlush(message);
            try {
                //等待登陆成功后被唤醒
                COUNT_DOWN.await();
            } catch (InterruptedException e) {

            }
            if(!LOGIN_FLAG.get()){
                //如果登陆失败，退出并关闭连接
                log.error("登陆失败，用户名或密码错误");
                ctx.channel().close();
                return;
            }
            //登陆成功后，继续接受命令
            while (true){
                System.out.println("==================================");
                System.out.println("send [username] [content]");
                System.out.println("gsend [group name] [content]");
                System.out.println("gcreate [group name] [m1,m2,m3...]");
                System.out.println("gmembers [group name]");
                System.out.println("gjoin [group name]");
                System.out.println("gquit [group name]");
                System.out.println("quit");
                System.out.println("==================================");
                //等待命令
                String command = scanner.nextLine();
                String[] commands = command.split(" ");
                switch (commands[0]){
                    case "send":
                        //发送消息
                        ctx.writeAndFlush(new ChatRequestMessage(username,commands[1],commands[2]));
                        break;
                    case "gsend":
                        //发送组消息
                        ctx.writeAndFlush(new GroupChatRequestMessage(username,commands[1],commands[2]));
                        break;
                    case "gcreate":
                        //创建组
                        HashSet<String> members = new HashSet<>();
                        for(String s:commands[2].split(",")){
                            members.add(s);
                        }
                        members.add(username);
                        ctx.writeAndFlush(new GroupCreateRequestMessage(commands[1],members));
                        break;
                    case "gmembers":
                        //查看组成员
                        ctx.writeAndFlush(new GroupMembersRequestMessage(commands[1]));
                        break;
                    case "gjoin":
                        //加入组
                        ctx.writeAndFlush(new GroupJoinRequestMessage(username,commands[1]));
                        break;
                    case "gquit":
                        //退出聊天组
                        ctx.writeAndFlush(new GroupQuitRequestMessage(username,commands[1]));
                        break;
                    case "quit":
                        //退出
                        ctx.channel().close();
                        break;
                }
            }
        },"system in")
                .start();
    }

    //读取服务器端响应的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof LoginResponseMessage){
            LoginResponseMessage response = (LoginResponseMessage) msg;
            //设置是否登陆成功
            LOGIN_FLAG.set(response.isSuccess());
            //唤醒等待登陆消息的线程
            COUNT_DOWN.countDown();
        }
        ctx.fireChannelRead(msg);
    }
}
