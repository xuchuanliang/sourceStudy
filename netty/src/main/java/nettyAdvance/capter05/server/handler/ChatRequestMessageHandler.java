package nettyAdvance.capter05.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.ChatRequestMessage;
import nettyAdvance.capter05.message.ChatResponseMessage;
import nettyAdvance.capter05.server.session.SessionFactory;

import java.util.Objects;

/**
 * 处理聊天消息的的处理器
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String content = msg.getContent();
        //获取发动目标的channel
        Channel channel = SessionFactory.getSession().getChannel(msg.getTo());
        if(Objects.isNull(channel)){
            //对方不在线，告诉发送者
            ctx.channel().writeAndFlush(new ChatResponseMessage(false,msg.getTo()+"不在线"));
        }else{
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(),content));
        }
    }
}
