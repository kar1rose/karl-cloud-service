package org.karl.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.decoder.ByteToStrDecoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author KARL ROSE
 * @date 2020/4/28 15:00
 **/
@Slf4j(topic = "Netty Server")
public class NettyDiscardServer {

    private static final int PORT = 8888;
    ServerBootstrap server = new ServerBootstrap();

    public void start() {
        //创建反应器线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //1.设置反应器线程组
            server.group(bossGroup, workGroup);
            //2.设置NIO类型的通道
            server.channel(NioServerSocketChannel.class);
            //3.设置监听端口
            server.localAddress(new InetSocketAddress(PORT));
            //4.通道参数
            server.option(ChannelOption.SO_KEEPALIVE, true);
            server.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            server.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //5.装配子通道流水线
            server.childHandler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个通道
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //流水线管理子通道中的handler
                    //向子通道流水线添加一个handler处理器
                    ch.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4))
                            .addLast(new StringDecoder(StandardCharsets.UTF_8))
                            /*.addLast("decoder", new HttpRequestDecoder())
                            .addLast("encoder", new HttpResponseEncoder())
                            .addLast("aggregator", new HttpObjectAggregator(512 * 1024))*/
                            .addLast(NettyDiscardServerHandler.INSTANCE)
                    ;
                }
            });
            //6.绑定服务器
            ChannelFuture channelFuture = server.bind().sync();
            log.info("server started success {}", channelFuture.channel().localAddress());
            //7.等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            //8.关闭event loop  group
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyDiscardServer().start();
    }
}
