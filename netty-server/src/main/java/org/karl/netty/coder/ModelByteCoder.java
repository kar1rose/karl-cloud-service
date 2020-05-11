package org.karl.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.karl.netty.model.User;

import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/5/11 14:26
 **/
public class ModelByteCoder extends ByteToMessageCodec<User> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, ByteBuf byteBuf) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
