package nettyAdvance.capter05.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 空间检测处理器，可以用来发送ping以及pong包
 */
@ChannelHandler.Sharable
public class ServerIdleHandler extends ChannelDuplexHandler {

    public static final int READER_IDLE_TIME = 10;
    public static final int WRITE_IDLE_TIME = 0;
    public static final int ALL_IDLE_TIME = 0;

    private static final Logger log = LoggerFactory.getLogger(ServerIdleHandler.class);
    /**
     * 当发生空闲检测时会调用到这个方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent i = (IdleStateEvent) evt;
            if(i.state()== IdleState.READER_IDLE){
//                log.error("channel:{}，已经{}秒没有向我发送数据了，考虑关闭或者发送ping包",ctx.channel(),READER_IDLE_TIME);
            }
        }
    }
}
