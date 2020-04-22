package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * socket channel 发送文件
 *
 * @author karl.rose
 * @date 2020/4/22 11:07
 **/
@Slf4j
public class NioSendClient {

    private final Charset charset = StandardCharsets.UTF_8;

    public void sendFile(String src, String dest) {
        try {
            File srcFile = new File(src);
            if (!srcFile.exists()) {
                log.error("file not exists >>> {}", src);
                throw new NoSuchFieldException();
            }
            FileChannel fileChannel = new FileInputStream(srcFile).getChannel();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(
                    new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT)
            );
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()) {
                log.info("正在连接>>>");
            }
            log.info(">>>连接成功>>>");
            ByteBuffer fileNameByteBuffer = charset.encode(dest);
            socketChannel.write(fileNameByteBuffer);
            ByteBuffer buffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
            buffer.putLong(srcFile.length());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            log.info("开始传输>>>");
            int length = 0;
            long progress = 0;
            while ((length = fileChannel.write(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                progress += length;
                log.info("|" + (100 * progress / srcFile.length()) + "%|");
            }
            if (length == -1) {
                fileChannel.close();
                //发送结束标志
                socketChannel.shutdownOutput();
                socketChannel.close();
            }
            log.info("===文件传输成功===");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new NioSendClient().sendFile("","");
    }

}
