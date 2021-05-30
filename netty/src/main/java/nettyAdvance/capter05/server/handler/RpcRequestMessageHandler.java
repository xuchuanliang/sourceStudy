package nettyAdvance.capter05.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.RpcRequestMessage;
import nettyAdvance.capter05.message.RpcResponseMessage;
import nettyAdvance.capter05.server.service.HelloService;
import nettyAdvance.capter05.server.service.ServicesFactory;

import java.lang.reflect.Method;

/**
 * 处理rpc请求，通过反射调用对应的的服务类的服务方法
 */
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) {
        System.out.println("==============================+"+msg.getMethodName());
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setSequenceId(msg.getSequenceId());
        try {
            //通过反射调用对象的方法
            Class<?> clazz = Class.forName(msg.getInterfaceName());
            HelloService service = (HelloService) ServicesFactory.getService(clazz);
            Method method = clazz.getMethod(msg.getMethodName(), msg.getParameterTypes());
            Object result = method.invoke(service, msg.getParameterValue());
            rpcResponseMessage.setReturnValue(result);
        } catch (Exception e) {
            e.printStackTrace();
            rpcResponseMessage.setExceptionValue(new Exception("远程调用失败："+e.getMessage()));
        }
        //将调用结果响应给客户端
        ctx.writeAndFlush(rpcResponseMessage);
    }
}
