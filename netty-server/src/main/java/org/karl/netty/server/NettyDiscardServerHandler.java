package org.karl.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author KARL ROSE
 * @date 2020/4/28 15:31
 **/
@Slf4j(topic = "Server Handler")
public class NettyDiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            log.info("message received >>>:{}", new String(bytes, StandardCharsets.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
