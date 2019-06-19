package com.tomato.framework.rpc.network.server;

import com.tomato.framework.rpc.network.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer implements Server {
    
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    
    @Override
    public void start() {
        //开启一个boss线程组
        EventLoopGroup boos = new NioEventLoopGroup(1);
        //开始一个工作线程组，默认cpu核数*2大小
        EventLoopGroup wokers = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture channelFuture = bootstrap.group(boos, wokers).channel(NioServerSocketChannel.class).option(
                ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO)).bind(PORT)
                .sync();
            // Wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
    }
}
