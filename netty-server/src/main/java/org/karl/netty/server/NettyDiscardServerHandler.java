package org.karl.netty.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.karl.netty.model.User;

/**
 * @author KARL ROSE
 * @date 2020/4/28 15:31
 **/
@Slf4j(topic = "入站处理器")
@ChannelHandler.Sharable
public class NettyDiscardServerHandler extends ChannelInboundHandlerAdapter {

    public static final NettyDiscardServerHandler INSTANCE = new NettyDiscardServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead调用");
        String str = (String) msg;
        User user = JSON.parseObject(str, User.class);
        log.info("message received >>>:{}", user);

        /*log.info("写回前:{}", in.refCnt());
        ChannelFuture channelFuture = ctx.writeAndFlush(msg);
        channelFuture.addListener((channelFutureListener) -> {
            log.info("写回后:{}", in.refCnt());
        });*/
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered调用");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered调用");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive调用");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive调用");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete调用");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered调用");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged调用");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught调用");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handler被调用");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
