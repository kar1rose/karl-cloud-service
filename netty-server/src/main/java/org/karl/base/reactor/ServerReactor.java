package org.karl.base.reactor;

import org.karl.base.socket.NioConstants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 反应器
 *
 * @author karl.rose
 * @date 2020/4/27 17:24
 **/
public class ServerReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;

    ServerReactor() throws IOException {
        //1.获取选择器
        selector = Selector.open();
        //2.获取通道
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT));
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new AcceptorHandler());
    }

    @Override
    public void run() {
        //选择器轮询
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                for (SelectionKey selectionKey : selected) {
                    dispatch(selectionKey);
                }
                selected.clear();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void dispatch(SelectionKey key) {
        Runnable handler = (Runnable) key.attachment();
        if (handler != null) {
            handler.run();
        }

    }

    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            //接受新连接
            //为新连接创建一个输入输出的handler
            try {
                SocketChannel channel = serverSocketChannel.accept();
                if(channel!=null){
                    new CustomHandler(selector,channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new ServerReactor()).start();
    }
}
