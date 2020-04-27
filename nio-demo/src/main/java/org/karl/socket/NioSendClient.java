package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * socket channel 发送文件
 *
 * @author karl.rose
 * @date 2020/4/22 11:07
 **/
@Slf4j
public class NioSendClient {

    private final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();

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
            log.info("===连接成功===");
            //1.传输文件名
            ByteBuffer fileNameByteBuffer = StandardCharsets.UTF_8.encode(dest);
            socketChannel.write(fileNameByteBuffer);
            //2.传输文件大小
            ByteBuffer buffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
            buffer.putLong(srcFile.length());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();

            log.info(">>>开始传输<<<");
            int length;
            long progress = 0;
            while ((length = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                progress += length;
                log.info("|" + (100 * progress / srcFile.length()) + "%|");
            }
            if (length == -1) {
                log.info("===文件传输结束===");
                fileChannel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }
            log.info("===文件传输成功===");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new NioSendClient().sendFile("D:/安装包/test.txt", UUID.randomUUID().toString() + ".txt");
    }

}
