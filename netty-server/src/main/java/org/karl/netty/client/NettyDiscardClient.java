package org.karl.netty.client;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.encoder.StrToByteEncoder;
import org.karl.netty.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author KARL ROSE
 * @date 2020/4/28 16:04
 **/
@Slf4j
public class NettyDiscardClient {

    private final static int SERVER_PORT = 8888;
    private final static String SERVER_IP = "127.0.0.1";
    Bootstrap b = new Bootstrap();

    public void start() {
        //创建reactor 线程组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            //1 设置reactor 线程组
            b.group(workerLoopGroup);
            //2 设置nio类型的channel
            b.channel(NioSocketChannel.class);
            //3 设置监听端口
            b.remoteAddress(SERVER_IP, SERVER_PORT);
            //4 设置通道的参数
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //5 装配子通道流水线
            b.handler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个channel
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // pipeline管理子通道channel中的Handler
                    // 向子channel流水线添加一个handler处理器
                    ch.pipeline()
                            .addLast(new LengthFieldPrepender(4))
                            .addLast(new StringEncoder(StandardCharsets.UTF_8));

                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture futureListener) ->
            {
                if (futureListener.isSuccess()) {
                    log.info("EchoClient客户端连接成功!");

                } else {
                    log.info("EchoClient客户端连接失败!");
                }

            });

            // 阻塞,直到连接完成
            f.sync();
            Channel channel = f.channel();

//            Scanner scanner = new Scanner(System.in);
//            log.info("请输入发送内容:");

            for (int i = 0; i < 1000; i++) {
                User user = User.builder().id(System.currentTimeMillis()).username("乔丹").password("$2a$10$kE.GkxCkL4DT7pFlVxF9z.XZje/41dPeS6cZjsqPMzkiNIawsKhuS").build();
                channel.writeAndFlush(JSON.toJSONString(user));
            }

            /*while (scanner.hasNext()) {
                String next = scanner.next();
                byte[] bytes = next.getBytes(StandardCharsets.UTF_8);
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
            }*/
//            log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
            log.error("连接断开");
        }

    }


    public static void main(String[] args) {
        new NettyDiscardClient().start();
    }
}
