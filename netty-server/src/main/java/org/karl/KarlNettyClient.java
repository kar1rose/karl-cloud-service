package org.karl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.karl.handler.CustomNettyClientHandler;

import java.net.InetSocketAddress;

/**
 * @author KARL ROSE
 * @date 2020/4/7 11:28
 **/
@Slf4j
public class KarlNettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int MAX = 10;
    private static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(HOST, PORT))
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new CustomNettyClientHandler());
                        }
                    });

            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            ChannelFuture f = bootstrap.connect().sync();
            Channel channel = f.channel();

            int index = 1;
            String msg = "hello world";
            while (index <= MAX) {
                channel.writeAndFlush(msg);
                Thread.sleep(2000);
                index++;
            }
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
