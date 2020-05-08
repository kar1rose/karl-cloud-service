package org.karl.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 字符串解码器
 *
 * @author karl.rose
 * @date 2020/5/8 15:12
 **/
@Slf4j
public class ByteToStrDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        /*if (in.readableBytes() < 4) {
            log.debug("消息头未满，返回");
            return;
        }
        in.markReaderIndex();
        int length = in.readInt();
        log.info("长度={}", length);
        if (in.readableBytes() < length) {
            log.debug("长度不够，返回");
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        out.add(new String(bytes, StandardCharsets.UTF_8));*/

        log.info(in.hasArray() ? "堆内存" : "直接内存");
        byte[] bytes = new byte[in.readableBytes()];
        //read bytes会清空msg中的可读数组
        in.readBytes(bytes);
        String str = new String(bytes, StandardCharsets.UTF_8);
        out.add(str);

    }
}
