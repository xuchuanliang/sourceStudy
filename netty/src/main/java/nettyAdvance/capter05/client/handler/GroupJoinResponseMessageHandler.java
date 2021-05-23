package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupJoinResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 申请加入组响应消息处理器
 */
@ChannelHandler.Sharable
public class GroupJoinResponseMessageHandler extends SimpleChannelInboundHandler<GroupJoinResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(GroupJoinResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponseMessage msg) throws Exception {
        log.error("加入组响应消息：{}",msg);
    }
}
