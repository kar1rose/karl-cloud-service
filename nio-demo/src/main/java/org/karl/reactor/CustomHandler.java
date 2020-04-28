package org.karl.reactor;

import org.karl.socket.NioConstants;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * IO 处理器
 *
 * @author karl.rose
 * @date 2020/4/27 17:35
 **/
public class CustomHandler implements Runnable {

    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    static final int RECEIVING = 0, SENDING = 1;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
    int state = RECEIVING;

    public CustomHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == SENDING) {
                //写入通道
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECEIVING;
            } else if (state == RECEIVING) {
                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
