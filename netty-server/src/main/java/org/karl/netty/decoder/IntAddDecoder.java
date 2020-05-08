package org.karl.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 整数相加解码器
 *
 * @author karl.rose
 * @date 2020/5/8 15:51
 **/
@Slf4j(topic = "加法处理器")
public class IntAddDecoder extends ReplayingDecoder<IntAddDecoder.Status> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        switch (state()) {
            case PARSE_1:
                n1 = in.readInt();
                log.info("n1={}",n1);
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                n2 = in.readInt();
                log.info("n2={}",n2);
                Integer sum = n1 + n2;
                log.info("sum={}",sum);
                out.add(sum);
                checkpoint(Status.PARSE_1);
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

    private int n1;
    private int n2;

    public IntAddDecoder() {
        super(Status.PARSE_1);
    }
}
