package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupChatResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 群消息响应处理器
 */
@ChannelHandler.Sharable
public class GroupChatResponseMessageHandler extends SimpleChannelInboundHandler<GroupChatResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(GroupChatResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponseMessage msg) throws Exception {
        log.error("接收到群聊的响应消息：{}",msg);
    }
}
