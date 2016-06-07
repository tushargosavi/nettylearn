package com.tugo.learn.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter
{
  @Override
  public void channelActive(final ChannelHandlerContext ctx) throws Exception
  {
    final ByteBuf buf = ctx.alloc().buffer(4);
    buf.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

    final ChannelFuture f = ctx.writeAndFlush(buf);
    f.addListener(new ChannelFutureListener()
    {
      public void operationComplete(ChannelFuture channelFuture) throws Exception
      {
        assert f == channelFuture;
        ctx.close();
      }
    });
    super.channelActive(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
  {
    cause.printStackTrace();
    ctx.close();
  }
}
