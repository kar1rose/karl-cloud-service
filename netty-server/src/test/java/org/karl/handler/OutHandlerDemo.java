package org.karl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 出站demo测试类
 *
 * @author karl.rose
 * @date 2020/5/7 13:47
 **/
@Slf4j
public class OutHandlerDemo {

    static class SimpleHandlerA extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("SimpleHandlerA回调");
            ByteBuf in = (ByteBuf) msg;
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            log.info("message received >>>:{}", new String(bytes, StandardCharsets.UTF_8));
            super.write(ctx, msg, promise);
        }
    }

    static class SimpleHandlerB extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("SimpleHandlerB回调");
            super.write(ctx, msg, promise);
        }
    }

    static class SimpleHandlerC extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("SimpleHandlerC回调");
            super.write(ctx, msg, promise);
        }
    }

    public static void main(String[] args) {
        ChannelInitializer<EmbeddedChannel> ci = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new SimpleHandlerA())
                        .addLast(new SimpleHandlerB())
                        .addLast(new SimpleHandlerC());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(ci);
        ByteBuf byteBuf = Unpooled.buffer();
        String next = "hello world";
        byte[] bytes = next.getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        channel.writeOutbound(byteBuf);
        byteBuf.writeBytes(bytes);
        channel.writeOutbound(byteBuf);
        channel.close();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
