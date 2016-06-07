package com.tugo.learn.netty.time;

import com.tugo.learn.netty.discard.DiscardServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer
{
  private int port;

  public TimeServer(int port) {
    this.port = port;
  }

  public void run() throws Exception
  {
    EventLoopGroup baseGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(baseGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>()
        {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception
          {
            System.out.println("connected to new client " + ch.remoteAddress());
            ch.pipeline().addLast(new TimeServerHandler());
          }
        })
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);

      ChannelFuture f = b.bind(port).sync();

      f.channel().closeFuture().sync();
    } finally {
      baseGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception
  {
    int port;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    } else {
      port = 8080;
    }
    new TimeServer(port).run();
  }
}
