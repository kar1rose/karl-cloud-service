package org.karl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
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
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int MAX = 10;
    private static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(HOST, PORT))
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline().addLast(new CustomNettyClientHandler());
                    }
                });

        ChannelFuture f = bootstrap.connect().sync();
        Channel channel = f.channel();
        channel.closeFuture().sync();

        int index = 1;
        while (index <= MAX) {
            String msg = "hello world";
            log.info(msg);
            channel.writeAndFlush(msg);
            Thread.sleep(2000);
            index++;
        }
    }
}
