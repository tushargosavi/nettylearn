package com.tugo.learn.netty.time;

import java.util.Date;

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
      long time = buf.readUnsignedInt();
      long currentTimeMillis = (time - 2208988800L) * 1000L;
      System.out.print("time is in millis " + currentTimeMillis);
      System.out.println(new Date(currentTimeMillis));
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
