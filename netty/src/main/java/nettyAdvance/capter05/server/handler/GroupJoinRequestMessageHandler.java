package nettyAdvance.capter05.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupJoinRequestMessage;
import nettyAdvance.capter05.message.GroupJoinResponseMessage;
import nettyAdvance.capter05.server.session.Group;
import nettyAdvance.capter05.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Objects;

/**
 * 加入群请求消息处理器
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(), msg.getUsername());
        if(Objects.isNull(group)){
            //组不存在
            ctx.writeAndFlush(new GroupJoinResponseMessage(false,"组不存在"));
        }else{
            //加入成功，通知群成员
            List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
            for(Channel c:membersChannel){
                if(c.isActive()){
                    c.writeAndFlush(new GroupJoinResponseMessage(true,"欢迎【"+msg.getUsername()+"】加入组"));
                }
            }
        }
    }
}
