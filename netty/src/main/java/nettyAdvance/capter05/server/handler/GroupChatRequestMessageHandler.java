package nettyAdvance.capter05.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupChatRequestMessage;
import nettyAdvance.capter05.message.GroupChatResponseMessage;
import nettyAdvance.capter05.server.session.GroupSessionFactory;

import java.util.List;

/**
 * 群聊请求消息处理器
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        //发送消息到群里所有成员
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        for(Channel c:membersChannel){
            if(checkActive(c)){
                c.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(),msg.getContent()));
            }
        }
    }

    /**
     * 检查channel是否在线
     * @param channel
     * @return
     */
    private boolean checkActive(Channel channel){
        return channel.isActive();
    }
}
