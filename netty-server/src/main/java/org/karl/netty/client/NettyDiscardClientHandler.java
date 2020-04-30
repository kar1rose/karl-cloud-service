package org.karl.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author KARL ROSE
 * @date 2020/4/28 16:06
 **/
@Slf4j
public class NettyDiscardClientHandler extends ChannelInboundHandlerAdapter {

    public static final NettyDiscardClientHandler INSTANCE = new NettyDiscardClientHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        log.info("client received: " + new String(arr, StandardCharsets.UTF_8));
        in.release();
    }
}
