package com.tugo.learn.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TimeClientHandler extends ChannelInboundHandlerAdapter
{
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
  {
    ByteBuf buf = (ByteBuf)msg;
    try {
      int time = buf.readInt();
      System.out.print("time is " + time);
      ctx.close();
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
  {
    cause.printStackTrace();
    ctx.close();
  }
}
