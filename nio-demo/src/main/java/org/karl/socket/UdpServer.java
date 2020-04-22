package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * udp server端
 *
 * @author karl.rose
 * @date 2020/4/22 12:29
 **/
@Slf4j
public class UdpServer {

    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(
                new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT)
        );
        log.info("UDP server start success");
        //开启通道选择器
        Selector selector = Selector.open();
        //注册read事件
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select()>0){
            Iterator<SelectionKey>iterator =selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isReadable()){
                    SocketAddress client = datagramChannel.receive(buffer);
                    buffer.flip();
                    log.info(new String(buffer.array(),0,buffer.limit()));
                    buffer.clear();
                }
            }
            //去除当前事件
            iterator.remove();
        }
        selector.close();
        datagramChannel.close();


    }

    public static void main(String[] args) throws IOException {
        new UdpServer().receive();
    }
}
