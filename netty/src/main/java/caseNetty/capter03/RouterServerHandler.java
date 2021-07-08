package caseNetty.capter03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果是实现了ChannelInboundHandlerAdapter，重写了channelRead方法，要么需要调用ctx.fireChannelRead(msg)沿着调用链继续向下走，到最后由netty进行释放；
 * 如果调用结束，没有调用任何netty方法进行继续在调用链上传输，那么需要手动使用ReferenceCountUtil.release(msg)ByteBuf内存进行释放，否则会发生内存泄漏
 */
public class RouterServerHandler extends ChannelInboundHandlerAdapter {
    static ExecutorService single = Executors.newSingleThreadExecutor();
    PooledByteBufAllocator allocator = new PooledByteBufAllocator();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        single.execute(()->{
            ByteBuf responseMessage = allocator.heapBuffer(bytes.length);
            responseMessage.writeBytes(bytes);
            ctx.writeAndFlush(responseMessage);
        });

    }
}
