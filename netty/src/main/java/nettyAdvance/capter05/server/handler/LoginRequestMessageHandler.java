package nettyAdvance.capter05.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyAdvance.capter05.message.LoginRequestMessage;
import nettyAdvance.capter05.message.LoginResponseMessage;
import nettyAdvance.capter05.message.Message;
import nettyAdvance.capter05.server.service.UserServiceFactory;
import nettyAdvance.capter05.server.session.SessionFactory;

/**
 * 处理登陆请求的登陆消息
 * 由于不存储变量，所以可以共享
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        //登陆成功后，保存会话信息
        SessionFactory.getSession().bind(ctx.channel(),msg.getUsername());
        Message response = login
                ? new LoginResponseMessage(true, "登陆成功")
                : new LoginResponseMessage(false, "登陆失败");
        //将响应消息写出到客户端
        ctx.writeAndFlush(response);
    }
}
