package org.karl.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.server.NettyDiscardServerHandler;

import java.nio.charset.StandardCharsets;

/**
 * 入站处理器测试
 *
 * @author karl.rose
 * @date 2020/5/6 17:15
 **/
@Slf4j
public class InHandlerDemo {

    static class SimpleHandlerA extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("SimpleHandlerA 调用");
            ByteBuf in = (ByteBuf) msg;
            byte[] bytes = new byte[in.readableBytes()];
            in.getBytes(0,bytes);
            String str = new String(bytes, StandardCharsets.UTF_8);
            log.info("message received >>>:{}", str);
            super.channelRead(ctx, msg);
        }
    }

    static class SimpleHandlerB extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("SimpleHandlerB 调用");
            ByteBuf in = (ByteBuf) msg;
            byte[] bytes = new byte[in.readableBytes()];
            in.getBytes(0,bytes);
            String str = new String(bytes, StandardCharsets.UTF_8);
            log.info("message received >>>:{}", str);
            super.channelRead(ctx, msg);
//            ctx.pipeline().remove("HB");
        }
    }

    static class SimpleHandlerC extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("SimpleHandlerC 调用");
            log.info(String.valueOf(msg));
            super.channelRead(ctx, msg);
        }
    }


    public static void main(String[] args) {
        ChannelInitializer<EmbeddedChannel> ci = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addFirst(new SimpleHandlerA())
//                        .addLast("HA",new SimpleHandlerA())
                        .addLast("HB",new SimpleHandlerB())
                        .addLast("HC",new SimpleHandlerC());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(ci);
        ByteBuf byteBuf = Unpooled.buffer();
        String next = "hello world你好";
        byte[] bytes = next.getBytes(StandardCharsets.UTF_8);

        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readerIndex());
        byteBuf.writeBytes(bytes);
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readerIndex());

        channel.writeInbound(byteBuf);
        channel.flush();
        channel.close();
    }
}
