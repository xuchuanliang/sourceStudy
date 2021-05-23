package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import nettyAdvance.capter05.message.PingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端空闲检测器，当发现5秒钟没有向服务器发送消息，那么就发送一个ping包
 */
@ChannelHandler.Sharable
public class ClientIdleHandler extends ChannelDuplexHandler {
    private static final Logger log = LoggerFactory.getLogger(ClientIdleHandler.class);
    public static final int READER_IDLE_TIME = 0;
    public static final int WRITE_IDLE_TIME = 5;
    public static final int ALL_IDLE_TIME = 0;
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent i = (IdleStateEvent) evt;
            if(i.state() == IdleState.WRITER_IDLE){
                log.error("已经{}秒没有向服务器写内容了，开始写ping包");
                ctx.writeAndFlush(new PingMessage());
            }
        }
    }
}
