package org.karl.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 池化测试
 *
 * @author karl.rose
 * @date 2020/5/7 15:19
 **/
@Slf4j
public class ByteBufTest {

    public static void main(String[] args) {
        String s = "这是一个疯狂的世界，需要大家一同守护，thank you ！";
        bufTest(s);
    }

    private static void bufTest(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteBuf heapBuf = achieveBuf(3);
        heapBuf.writeBytes(bytes);
        if (heapBuf.hasArray()) {
            //堆内存缓冲区
            byte[] array = heapBuf.array();
            int offSet = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            log.info(new String(array, offSet, length, StandardCharsets.UTF_8));
        }
        heapBuf.release();

        ByteBuf directBuf = achieveBuf(0);
        directBuf.writeBytes(bytes);
        if (!directBuf.hasArray()) {
            //数据存储在操作系统内存内
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            log.info(new String(array, StandardCharsets.UTF_8));
        }
        directBuf.release();
    }

    private static ByteBuf achieveBuf(int type) {
        ByteBuf byteBuf;
        switch (type) {
            case 1:
                byteBuf = ByteBufAllocator.DEFAULT.buffer();
                break;
            case 2:
                byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
                break;
            case 3:
                byteBuf = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
                break;
            default:
                byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
                break;
        }
        return byteBuf;
    }

    private static void demo() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        log.info("after create ,{}", byteBuf.refCnt());
        byteBuf.retain();
        log.info("after retain ,{}", byteBuf.refCnt());
        byteBuf.release();
        log.info("after release ,{}", byteBuf.refCnt());
        byteBuf.release();
        log.info("after release ,{}", byteBuf.refCnt());
        byteBuf.retain();
        log.info("after retain ,{}", byteBuf.refCnt());
    }
}
