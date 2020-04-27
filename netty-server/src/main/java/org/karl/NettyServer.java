package org.karl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;
import org.karl.handler.CustomNettyHandler;

import java.net.InetSocketAddress;

/**
 * @author KARL ROSE
 * @date 2020/4/7 11:27
 **/
@Slf4j
public class NettyServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            serverBootstrap
                    .group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {

                            /**
                             *
                             * 1.HttpRequestDecoder，用于解码request
                             * 2.HttpResponseEncoder，用于编码response
                             * 3.aggregator，消息聚合器（重要）。为什么能有FullHttpRequest这个东西，就是因为有他，HttpObjectAggregator，如果没有他，就不会有那个消息是FullHttpRequest的那段Channel，同样也不会有FullHttpResponse。
                             * 如果我们将 HttpObjectAggregator(512 * 1024)的参数含义是消息合并的数据大小，如此代表聚合的消息内容长度不超过512kb。
                             * 4.添加我们自己的处理接口
                             *
                             **/
                            ch.pipeline()
                                    .addLast("decoder", new HttpRequestDecoder())   // 1
                                    .addLast("encoder", new HttpResponseEncoder())  // 2
                                    .addLast("aggregator", new HttpObjectAggregator(512 * 1024))    // 3
                                    .addLast("handler", new CustomNettyHandler());

                            /*ch.pipeline()
                                    .addFirst(new LoggingHandler(LogLevel.DEBUG))
                                    .addLast(new HttpRequestDecoder(), new StringDecoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            log.info(JSON.toJSONString(ctx));
                                            super.channelActive(ctx);
                                        }
                                    })
                                    .addLast(new CustomNettyHandler());*/
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);


                    /*.childOption(ChannelOption.AUTO_READ, false);
            ChannelFuture f = serverBootstrap.bind().sync();
            f.channel().closeFuture().sync();*/
        }  finally {
            boos.shutdownGracefully().sync();
        }


    }
}
