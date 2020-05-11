package org.karl.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author KARL ROSE
 * @date 2020/5/11 11:51
 **/
@Slf4j
public class StrToByteEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String str, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(str.getBytes(StandardCharsets.UTF_8));
    }
}
