package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import nettyAdvance.capter05.message.RpcResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>(256);

    private static final Logger log = LoggerFactory.getLogger(RpcResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        //1.获取并移除该消息对应的promise，防止map中存在大量失效的promise
        Promise<Object> promise = PROMISES.remove(msg.getSequenceId());
        //2.根据成功或失败信息设置信息
        if(Objects.nonNull(msg.getExceptionValue())){
            //设置异常信息
            promise.setFailure(msg.getExceptionValue());
        }else{
            //设置正确信息
            promise.setSuccess(msg.getReturnValue());
        }
        //处理rpc响应消息，仅仅将消息打印出来
        log.error("msg is : {}",msg);
    }
}
