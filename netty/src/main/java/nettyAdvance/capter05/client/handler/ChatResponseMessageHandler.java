package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.ChatResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理接收到的聊天消息
 */
@ChannelHandler.Sharable
public class ChatResponseMessageHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(ChatResponseMessage.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        log.error("接收到{}的消息：{}",msg.getFrom(),msg.getContent());
    }
}
