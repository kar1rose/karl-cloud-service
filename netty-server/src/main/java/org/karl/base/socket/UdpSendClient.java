package org.karl.base.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * udp 文件传输
 *
 * @author karl.rose
 * @date 2020/4/22 12:16
 **/
@Slf4j
public class UdpSendClient {

    public void sendFile() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(NioConstants.BUFFER_SIZE);
        Scanner scanner = new Scanner(System.in);
        log.info("UDP client start success");
        log.info("please enter the message");
        while (scanner.hasNext()) {
            String next = scanner.next();
            buffer.put((System.currentTimeMillis() + ">>>" + next).getBytes());
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress(NioConstants.NIO_IP, NioConstants.NIO_PORT));
            buffer.clear();
        }
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new UdpSendClient().sendFile();
    }
}
