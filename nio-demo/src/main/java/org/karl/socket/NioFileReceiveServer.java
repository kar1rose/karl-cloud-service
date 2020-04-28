package org.karl.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * file receive 服务端
 *
 * @author karl.rose
 * @date 2020/4/22 15:41
 **/
@Slf4j(topic = "文件接收服务器")
public class NioFileReceiveServer {

    public static void main(String[] args) throws IOException {
        NioFileReceiveServer server = new NioFileReceiveServer();
        server.start();
    }

    private final Charset charset = StandardCharsets.UTF_8;

    private static final String ROOT = "/Users/arvato/data";

    static class Client {
        String fileName;
        long fileLength;
        long startTime;
        InetSocketAddress address;
        FileChannel fileChannel;
    }

    Map<SelectableChannel, Client> clientMap = new HashMap<>(16);
    ByteBuffer byteBuffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);

    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT);
        serverSocket.bind(address);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("server is listening");
        while (selector.select() > 0) {
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey selectionKey = selectionKeys.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    Client client = new Client();
                    client.address = (InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel, client);
                    log.info("{} 连接成功", socketChannel.getRemoteAddress());
                } else if (selectionKey.isReadable()) {
                    //处理接收的数据
                    dataHandler(selectionKey);
                }
                selectionKeys.remove();
            }
        }

    }

    private void dataHandler(SelectionKey key) throws IOException {
        Client client = clientMap.get(key.channel());
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        try {
            byteBuffer.clear();
            while ((num = socketChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                if (client.fileName == null) {
                    String fileName =  charset.decode(byteBuffer).toString();
                    log.info("传输文件名:{}", fileName);
                    File dest = new File(ROOT);
                    if (!dest.exists()) {
                        if (!dest.mkdir()) {
                            log.error("目录创建失败");
                        }
                    }
                    client.fileName = fileName;
                    String target = dest.getAbsolutePath() + "/" + fileName;
                    File file = new File(target);
                    log.info("文件保存路径:{}", file.getAbsolutePath());
                    if (!file.exists()) {
                        if (file.createNewFile()) {
                            log.info("文件创建成功");
                        } else {
                            log.error("文件创建失败");
                        }
                    }
                    client.fileChannel = new FileOutputStream(file).getChannel();
                } else if (0 == client.fileLength) {
                    client.fileLength = byteBuffer.getLong();
                    log.info("传输文件大小:{}", client.fileLength);
                    client.startTime = System.currentTimeMillis();
                    log.info("===服务端接收开始===");
                } else {
                    client.fileChannel.write(byteBuffer);
                }
                byteBuffer.clear();
            }
        } catch (Exception e) {
            key.cancel();
            log.error(e.getMessage());
        }

        if (num == -1) {
            client.fileChannel.close();
            log.info("上传完成");
            key.cancel();
            log.info("文件{}传输成功", client.fileName);
            log.info("文件大小{}", client.fileLength);
            log.info("传输时间>>>{}", (System.currentTimeMillis() - client.startTime));
        }
    }


}
