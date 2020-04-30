package org.karl.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author KARL ROSE
 * @date 2020/4/28 15:31
 **/
@Slf4j(topic = "Server Handler")
public class NettyServerDiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            log.info("收到信息>>>:");
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
