package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupCreateResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 群聊创建响应消息处理器
 */
@ChannelHandler.Sharable
public class GroupCreateResponseMessageHandler extends SimpleChannelInboundHandler<GroupCreateResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(GroupCreateResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateResponseMessage msg) throws Exception {
        log.error("群聊创建响应消息：{}",msg);
    }
}
