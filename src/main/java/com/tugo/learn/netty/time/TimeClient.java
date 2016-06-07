package com.tugo.learn.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class TimeClient
{
  public static void main(String[] args)
  {
    int port = 8080;
    if (args.length > 0)
      port = Integer.parseInt(args[0]);

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(workerGroup)
        .channel(SocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>()
        {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception
          {
            ch.pipeline().addLast(new TimeClientHandler());
          }
        });
    }catch ()
  }
}
