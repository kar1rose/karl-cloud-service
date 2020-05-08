package org.karl.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 分包解码器
 *
 * @author karl.rose
 * @date 2020/5/8 16:49
 **/
@Slf4j
public class StrReplayDecoder extends ReplayingDecoder<StrReplayDecoder.Status> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        switch (state()) {
            case PARSE_1:
                length = in.readInt();
                log.info("字符长度:{}",length);
                bytes = new byte[length];
                checkpoint(StrReplayDecoder.Status.PARSE_2);
                break;
            case PARSE_2:
                in.readBytes(bytes,0,length);
                String str = new String(bytes, StandardCharsets.UTF_8);
                out.add(str);
                checkpoint(StrReplayDecoder.Status.PARSE_1);
                break;
            default:
                break;
        }
    }

    enum Status {
        /**
         * PARSE_1
         * PARSE_1
         **/
        PARSE_1,
        PARSE_2;
    }

    private int length;
    private byte[] bytes;

    public StrReplayDecoder() {
        super(StrReplayDecoder.Status.PARSE_1);
    }
}
