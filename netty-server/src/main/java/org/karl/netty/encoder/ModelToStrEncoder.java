package org.karl.netty.encoder;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.model.User;

import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/5/11 11:51
 **/
@Slf4j
public class ModelToStrEncoder extends MessageToMessageEncoder<User> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, List<Object> list) throws Exception {
        String str = JSON.toJSONString(user);
        list.add(str);
    }
}
