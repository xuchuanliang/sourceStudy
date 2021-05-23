package nettyAdvance.capter05.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.GroupQuitRequestMessage;
import nettyAdvance.capter05.message.GroupQuitResponseMessage;
import nettyAdvance.capter05.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Objects;

/**
 * 退出群聊请求消息处理器
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        if(Objects.isNull(GroupSessionFactory.getGroupSession().removeMember(msg.getGroupName(), msg.getUsername()))){
            //退出群聊失败
            ctx.writeAndFlush(new GroupQuitResponseMessage(false,"退出群聊失败，群聊不存在"));
        }else{
            //退出群聊成功，通知退出者
            ctx.writeAndFlush(new GroupQuitResponseMessage(true,"退出群聊成功"));
            //通知群里其他人
            List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
            for(Channel c:membersChannel){
                c.writeAndFlush(new GroupQuitResponseMessage(true,msg.getUsername()+"退出群聊"));
            }
        }
    }
}
