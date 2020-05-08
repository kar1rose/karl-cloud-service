package org.karl.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.decoder.ByteToStrDecoder;
import org.karl.netty.decoder.Str2ModelDecoder;
import org.karl.netty.model.User;

import java.nio.charset.StandardCharsets;
import java.util.Random;

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
            in.getBytes(0, bytes);
            String str = new String(bytes, StandardCharsets.UTF_8);
            log.info("message received >>>:{}", str);
            super.channelRead(ctx, msg);
        }
    }

    static class SimpleHandlerB extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("SimpleHandlerB 调用");
            log.info("message received >>>:{}", msg);
            super.channelRead(ctx, msg);
//            ctx.pipeline().remove("HB");
        }
    }


    public static void main(String[] args) {
        ChannelInitializer<EmbeddedChannel> ci = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline()
//                        .addFirst(new IntAddDecoder())
//                        .addLast("HA",new SimpleHandlerA())
//                        .addLast(new StrReplayDecoder())
//                        .addLast(new LineBasedFrameDecoder(1024))
                        .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
                        .addLast(new ByteToStrDecoder())
//                        .addLast(new Str2ModelDecoder())
                        .addLast("HB", new SimpleHandlerB());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(ci);
        /*ByteBuf byteBuf = Unpooled.buffer();
        User user = User.builder().id(System.currentTimeMillis()).username("rose").password("password").build();
        byte[] bytes = JSON.toJSONString(user).getBytes(StandardCharsets.UTF_8);

        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readerIndex());
        byteBuf.writeBytes(bytes);
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.readerIndex());
        channel.writeInbound(byteBuf);
        channel.flush();*/

        /*for (int i = 10; i < 21; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(i);
            channel.writeInbound(byteBuf);
        }

        channel.flush();*/

        byte[] bytes = "大家好，欢迎来自五湖四海的朋友 thank you".getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < 10; i++) {
            int random = new Random().nextInt(3) + 1;
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(random * bytes.length);
            for (int j = 0; j < random; j++) {
                byteBuf.writeBytes(bytes);
            }
//            byteBuf.writeBytes("\r\n".getBytes());
            channel.writeInbound(byteBuf);
        }
        channel.flush();
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.close();
    }
}
