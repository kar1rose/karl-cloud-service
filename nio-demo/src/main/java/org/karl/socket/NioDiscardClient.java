package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * discard 客户端
 *
 * @author karl.rose
 * @date 2020/4/22 15:19
 **/
@Slf4j(topic = "DISCARD CLIENT")
public class NioDiscardClient {

    public static void startClient(String msg) throws IOException {
        InetSocketAddress address = new InetSocketAddress(NioConstants.NIO_IP,NioConstants.NIO_PORT);
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()){
            log.info("connecting ...");
        }
        log.info("client connected succeed");
        ByteBuffer byteBuffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        //发送

        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();

    }

    public static void main(String[] args) throws IOException {
        NioDiscardClient.startClient("hello my dog");
    }
}
