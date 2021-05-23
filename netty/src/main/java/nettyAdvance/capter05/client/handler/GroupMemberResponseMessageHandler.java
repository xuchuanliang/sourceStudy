package nettyAdvance.capter05.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupMembersResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取组成员响应消息处理器
 */
@ChannelHandler.Sharable
public class GroupMemberResponseMessageHandler extends SimpleChannelInboundHandler<GroupMembersResponseMessage> {
    private static final Logger log = LoggerFactory.getLogger(GroupMemberResponseMessageHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersResponseMessage msg) throws Exception {
        log.error("获取组成员响应消息：{}",msg);
    }
}
