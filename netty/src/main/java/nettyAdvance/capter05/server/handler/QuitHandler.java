package nettyAdvance.capter05.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nettyAdvance.capter05.server.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理客户端链接正常或者异常断开的情况
 */
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(QuitHandler.class);

    /**
     * 链接正常断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("客户端{}的链接已经正常断开",ctx);
        SessionFactory.getSession().unbind(ctx.channel());
    }

    /**
     * 连接异常断开
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端{}的链接已经正常断开，异常是：{}",ctx,cause.getMessage());
        SessionFactory.getSession().unbind(ctx.channel());
    }
}
