package com.tugo.learn.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient
{
  public static void main(String[] args)
  {
    String host = "localhost";
    int port = 8080;
    if (args.length == 1)
      port = Integer.parseInt(args[0]);
    if (args.length >= 2) {
      host = args[0];
      port = Integer.parseInt(args[1]);
    }

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(workerGroup)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>()
        {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception
          {
            ch.pipeline().addLast(new TimeClientHandler());
          }
        });

      ChannelFuture f = b.connect(host, port).sync();

      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}
