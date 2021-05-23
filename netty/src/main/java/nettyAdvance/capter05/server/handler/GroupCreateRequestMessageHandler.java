package nettyAdvance.capter05.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupCreateRequestMessage;
import nettyAdvance.capter05.message.GroupCreateResponseMessage;
import nettyAdvance.capter05.server.session.Group;
import nettyAdvance.capter05.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Objects;

/**
 * 创建群聊请求消息的处理器
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().createGroup(msg.getGroupName(), msg.getMembers());
        if(Objects.nonNull(group)){
            //创建失败，通知创建者
            ctx.writeAndFlush(new GroupCreateResponseMessage(false,msg.getGroupName()+"群创建失败！"));
        }else{
            //创建成功，通知创建者
            ctx.writeAndFlush(new GroupCreateResponseMessage(true,msg.getGroupName()+"群创建成功！"));
            //向群成员发送拉入群消息
            List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
            for(Channel c:membersChannel){
                c.writeAndFlush(new GroupCreateResponseMessage(true,"您已经成功被拉入群："+msg.getGroupName()));
            }
        }
    }
}
