package org.karl.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.model.User;

import java.nio.charset.StandardCharsets;

/**
 * @author KARL ROSE
 * @date 2020/5/11 11:53
 **/
@Slf4j
public class Test {
    public static void main(String[] args) {
        ChannelInitializer<EmbeddedChannel> ci = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new StrToByteEncoder())
                        .addLast(new ModelToStrEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(ci);
        /*for (int i = 0; i < 100; i++) {
            channel.write(i);
        }*/
        User user = User.builder().id(System.currentTimeMillis()).username("rose").password("password").build();
        channel.write(user);
        channel.flush();

        ByteBuf byteBuf = channel.readOutbound();
        while (byteBuf != null) {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            //read bytes会清空msg中的可读数组
            byteBuf.readBytes(bytes);
            String str = new String(bytes, StandardCharsets.UTF_8);
            log.info("out : {}", str);
            byteBuf = channel.readOutbound();
        }
        channel.close();
    }
}
