package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupQuitResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 退出群聊响应消息
 */
@ChannelHandler.Sharable
public class GroupQuitResponseMessageHandler extends SimpleChannelInboundHandler<GroupQuitResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(GroupQuitResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitResponseMessage msg) throws Exception {
        log.error("退出群聊响应消息：{}",msg);
    }
}
