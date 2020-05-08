package org.karl.netty.decoder;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.karl.netty.model.User;

import java.util.List;

/**
 * 泛型解码器
 *
 * @author karl.rose
 * @date 2020/5/8 17:13
 **/
public class Str2ModelDecoder extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        User user = JSON.parseObject(msg, User.class);
        out.add(user);
    }
}
