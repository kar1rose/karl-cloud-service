package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * discard 服务器
 *
 * @author karl.rose
 * @date 2020/4/22 14:34
 **/
@Slf4j(topic = "DISCARD SERVER")
public class NioDiscardServer {

    public void startServer() throws IOException {
        //1.获取选择器
        Selector selector = Selector.open();
        //2.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT));
        log.info("server start succeed");
        //4.注册“接受新连接”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //5.选择注册的时间进行轮询
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //6.获取选择键集合
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    //7.当选择键是“accept”时，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //8.将新事件注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
                    int length;
                    while ((length = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        log.info(new String(byteBuffer.array(), 0, length));
                        byteBuffer.clear();
                    }
                    socketChannel.close();
                }
                iterator.remove();
            }
        }
        serverSocketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new NioDiscardServer().startServer();
    }
}
